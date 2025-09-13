package com.hulkhiretech.payments.service.webhookProcessorsV1;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.service.interfaces.IProcessingNotifier;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// Occurs when a payment intent using a delayed payment method finally succeeds.
@Slf4j
@Service("v1.checkout.session.async_payment_succeeded")
@RequiredArgsConstructor
public class SessionAsyncCompletedProcessorV1 extends BaseWebhookProcessor
{
	private final IProcessingNotifier processingNotifier;

	@Override
	public <T extends StripeObject> void process(T eventObj)
	{
		final var session = (Session) eventObj;

		final var sessionId = session.getId();
		final var paymentStatus = session.getPaymentStatus();
		final var tid = session.getClientReferenceId();

		log.info("Checkout session completed: sessionId='{}', paymentStatus='{}', txnReference='{}'",
			sessionId,
			paymentStatus,
			tid
		);

		processingNotifier.notifySuccess(tid);
	}
}
