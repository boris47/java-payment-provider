package com.hulkhiretech.payments.controller;

import com.hulkhiretech.payments.service.interfaces.IWebhookServiceV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/webhook/v1")
@RequiredArgsConstructor
public class WebhookV1Controller
{
	private final IWebhookServiceV1 webhookService;

	@PostMapping()
	public ResponseEntity<Void> handleWebhook
	(
		@RequestHeader("Stripe-Signature") String sigHeader,
		@RequestBody String payload
	)
	{
		return webhookService.handleWebhook(sigHeader, payload);
	}
}