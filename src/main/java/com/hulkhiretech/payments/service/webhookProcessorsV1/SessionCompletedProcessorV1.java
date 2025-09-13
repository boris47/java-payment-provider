package com.hulkhiretech.payments.service.webhookProcessorsV1;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.service.interfaces.IProcessingNotifier;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// Occurs when a Checkout Session has been successfully completed.
@Slf4j
@Service("v1.checkout.session.completed")
@RequiredArgsConstructor
public class SessionCompletedProcessorV1 extends BaseWebhookProcessor
{
	private final IProcessingNotifier processingNotifier;
	
	@Override
	public <T extends StripeObject> void process(T eventObj)
	{
		final Session session = (Session) eventObj;
		
		final var sessionId = session.getId();
		final var paymentStatus = session.getPaymentStatus();
		final var tid = session.getClientReferenceId();

		log.info("Checkout session completed: sessionId='{}', paymentStatus='{}', txnReference='{}'",
			sessionId,
			String.format("%-10s", paymentStatus),
			tid
		);

		processingNotifier.notifySuccess(tid);
	}
}
