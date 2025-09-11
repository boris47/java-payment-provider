package com.hulkhiretech.payments.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulkhiretech.payments.controller.pojo.StripeEventV2;
import com.hulkhiretech.payments.service.interfaces.IWebhookServiceV2;
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
		
		log.info("Received event: id={}, type={}", event.getId(), event.getType());
		
	//	switch (event.getType())
	//	{
	//		case "checkout.session.completed":
	//		{
	//			String sessionId = (String) event.getData().getObject().get("id");
	//			String paymentStatus = (String) event.getData().getObject().get("payment_status");
	//			log.info("Checkout session completed: sessionId={}, paymentStatus={}", sessionId, paymentStatus);
	//			break;
	//		}
	//		case "payment_intent.succeeded":
	//		{
	//			String paymentIntentId = (String) event.getData().getObject().get("id");
	//			log.info("Payment intent succeeded: paymentIntentId={}", paymentIntentId);
	//			break;
	//		}
	//	}
	//	return;
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
