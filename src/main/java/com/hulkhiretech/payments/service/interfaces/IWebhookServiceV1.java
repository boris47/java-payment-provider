package com.hulkhiretech.payments.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface IWebhookServiceV1
{
	ResponseEntity<Void> handleWebhook(String sigHeader, String payload);
}
