package com.hulkhiretech.payments.alt.service.interfaces;

public interface StripeWebhookServiceInterface
{
	public void webhookCall(String sigHeader, String payload);
}