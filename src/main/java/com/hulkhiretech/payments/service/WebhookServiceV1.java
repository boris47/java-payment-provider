package com.hulkhiretech.payments.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.JsonSyntaxException;
import com.hulkhiretech.payments.service.interfaces.IWebhookServiceV1;
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
	
	@Async
	@Override
	public ResponseEntity<Void> handleWebhook(String sigHeader, String payload)
	{
		Event event = GetEvent(payload, sigHeader, kSecret);
		if (event == null)
		{
			log.error("Invalid webhook event received sigHeader: {}, payload: {}", sigHeader, payload);
			return ResponseEntity.badRequest().build();
		}
		
		switch (event.getType())
		{
			case "checkout.session.completed":
			{
				
				break;
			}
			case "payment_intent.succeeded":
			{
				log.info("Payment intent succeeded: paymentIntentId={}", event.getId());
				break;
			}
		}
		return ResponseEntity.ok().build();
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
