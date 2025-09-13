package com.hulkhiretech.payments.service.webhookProcessorsV2;

import java.util.Map;

public interface IWebhookProcessor
{
	void process(Map<String, Object> eventObj);
}
