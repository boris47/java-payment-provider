package com.hulkhiretech.payments.controller;

import com.hulkhiretech.payments.service.interfaces.IWebhookServiceV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/webhook/v2")
@RequiredArgsConstructor
public class WebhookV2Controller
{
	private final IWebhookServiceV2 webhookService;

	@PostMapping()
	public ResponseEntity<Void> handleWebhook
	(
		@RequestHeader("Stripe-Signature") String sigHeader,
		@RequestBody String payload
	)
	{
		webhookService.handleWebhook(sigHeader, payload);
		return ResponseEntity.ok().build();
	}
}