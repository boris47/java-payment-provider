package com.hulkhiretech.payments.service.interfaces;

public interface IWebhookServiceV2
{
	void handleWebhook(String sigHeader, String payload);
}
