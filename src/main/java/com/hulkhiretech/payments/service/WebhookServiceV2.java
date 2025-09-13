package com.hulkhiretech.payments.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulkhiretech.payments.controller.pojo.StripeEventV2;
import com.hulkhiretech.payments.service.interfaces.IWebhookServiceV2;
import com.hulkhiretech.payments.service.webhookProcessorsV2.IWebhookProcessor;
import com.hulkhiretech.payments.util.Constants;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.net.Webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public final class WebhookServiceV2 implements IWebhookServiceV2
{
	private final ObjectMapper objectMapper;
	
	private final Map<String, IWebhookProcessor> services;
	
	@Value("${stripe.webhook.secret.v2}")
	private String kSecret;
	
	@Async
	@Override
	public void handleWebhook(String sigHeader, String payload)
	{
		StripeEventV2 event = GetEvent(objectMapper, payload, sigHeader, kSecret);
		if (event == null)
		{
			log.error("Invalid webhook event received sigHeader: {}, payload: {}", sigHeader, payload);
			return;
		}
		
		final String version = "v2";
		final var eventType = event.getType();
		final var processor = services.get(version + "." + eventType);
		if (processor == null)
		{
			log.warn("No processor service ({}) found for event type: {}", version, eventType);
			return;
		}
		
		processor.process(event.getData().getObject());
	}
	
	
	private static StripeEventV2 GetEvent(ObjectMapper objectMapper, String payload, String sigHeader, String secret)
	{
		StripeEventV2 event = null;
		
		try
		{
			Webhook.Signature.verifyHeader(payload, sigHeader, secret, Constants.TOLERANCE_SECONDS);
		}
		catch (SignatureVerificationException e)
		{
			log.error("Error verifying webhook signature: " + e.getMessage());
			return null;
		}
		
		try
		{
			event = objectMapper.readValue(payload, StripeEventV2.class);
		}
		catch (JsonProcessingException e)
		{
			log.error("Error parsing payload: " + e.getMessage());
			return null;
		}
		
		return event;
	}
}
