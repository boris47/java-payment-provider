package com.hulkhiretech.payments.__alt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.__alt.service.interfaces.StripeWebhookServiceInterface;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("v1/stripe/webhook")
@RequiredArgsConstructor
public class StripeWebhookController
{
	private final StripeWebhookServiceInterface webhookInterface;
	
	@PostConstruct
	private void OnInit()
	{
		log.info("Initialized StripeWebhookController");
	}
	
	@PostMapping()
	public ResponseEntity<Void> handleStripeWebhook
	(
		@RequestHeader("Stripe-Signature") String sigHeader,
		@RequestBody String payload
	)
	{
		webhookInterface.webhookCall(sigHeader, payload);
		return ResponseEntity.ok().build();
	}
}
