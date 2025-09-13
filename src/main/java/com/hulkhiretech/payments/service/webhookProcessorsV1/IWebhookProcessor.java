package com.hulkhiretech.payments.service.webhookProcessorsV1;

import com.stripe.model.StripeObject;

public interface IWebhookProcessor
{
	<T extends StripeObject> void process(T eventObj);
}
