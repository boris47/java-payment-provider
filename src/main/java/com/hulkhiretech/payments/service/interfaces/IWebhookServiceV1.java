package com.hulkhiretech.payments.service.interfaces;

public interface IWebhookServiceV1
{
	void handleWebhook(String sigHeader, String payload);
}
