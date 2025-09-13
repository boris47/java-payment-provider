package com.hulkhiretech.payments.service.webhookProcessorsV2;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.service.interfaces.IProcessingNotifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// Occurs when a Checkout Session has been successfully completed.
@Slf4j
@Service("v2.checkout.session.completed")
@RequiredArgsConstructor
public class SessionCompletedProcessorV2 extends BaseWebhookProcessor
{
	private final IProcessingNotifier processingNotifier;

	@Override
	public void process(Map<String, Object> eventObj)
	{
		final var sessionId = getValue(eventObj, "id", String.class, null);
		final var paymentStatus = getValue(eventObj, "payment_status", String.class, null);
		final var tid = getValue(eventObj, "client_reference_id", String.class, null);

		log.info("Checkout session completed: sessionId='{}', paymentStatus='{}', txnReference='{}'",
			sessionId,
			String.format("%-10s", paymentStatus),
			tid
		);

		processingNotifier.notifySuccess(tid);
	}
}
