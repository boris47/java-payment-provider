package com.hulkhiretech.payments.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.JsonSyntaxException;
import com.hulkhiretech.payments.service.interfaces.IWebhookServiceV1;
import com.hulkhiretech.payments.service.webhookProcessorsV1.IWebhookProcessor;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public final class WebhookServiceV1 implements IWebhookServiceV1
{
	@Value("${stripe.webhook.secret.v1}")
	private String kSecret;
	
	private final Map<String, IWebhookProcessor> services;
	
	@Async
	@Override
	public void handleWebhook(String sigHeader, String payload)
	{
		Event event = GetEvent(payload, sigHeader, kSecret);
		if (event == null)
		{
			log.error("Invalid webhook event received sigHeader: {}, payload: {}", sigHeader, payload);
			return;
		}
		
		final String version = "v1";
		final var eventType = event.getType();
		final var processor = services.get(version + "." + eventType);
		if (processor == null)
		{
			log.warn("No processor service ({}) found for event type: {}", version, eventType);
			return;
		}
		
		final var stripeObjectOpt = event.getDataObjectDeserializer().getObject();
		if (stripeObjectOpt.isEmpty())
		{
			log.error("Missing event data object for event id: {}, type: {}", event.getId(), event.getType());
			return;
		}

		processor.process(stripeObjectOpt.get());
	}
	
	private static Event GetEvent(String payload, String sigHeader, String secret)
	{
		Event event = null;
		
		try
		{
			// Verify the event by signature
			event = Webhook.constructEvent(payload, sigHeader, secret);
		}
		// From ApiResource.INTERNAL_GSON.fromJson called in
		// StripeObject.deserializeStripeObject
		catch (JsonSyntaxException e)
		{
			// Invalid payload
			log.error("Error parsing payload: " + e.getMessage());
		}
		catch (SignatureVerificationException e)
		{
			// Invalid signature
			log.error("Error verifying webhook signature: " + e.getMessage());
		}
		
		return event;
	} 
}
