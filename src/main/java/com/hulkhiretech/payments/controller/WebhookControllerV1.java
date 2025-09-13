package com.hulkhiretech.payments.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.service.interfaces.IWebhookServiceV1;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/webhook/v1")
@RequiredArgsConstructor
public class WebhookControllerV1
{
	private final IWebhookServiceV1 webhookService;

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