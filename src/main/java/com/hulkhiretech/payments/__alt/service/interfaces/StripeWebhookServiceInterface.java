package com.hulkhiretech.payments.__alt.service.interfaces;

public interface StripeWebhookServiceInterface
{
	public void webhookCall(String sigHeader, String payload);
}