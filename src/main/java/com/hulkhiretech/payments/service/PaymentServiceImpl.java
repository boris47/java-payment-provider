package com.hulkhiretech.payments.service;

import org.springframework.stereotype.Service;
import com.hulkhiretech.payments.controller.pojo.RequestCreatePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseCreatePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseExpirePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseRetrievePayment;
import com.hulkhiretech.payments.service.helpers.CreatePaymentHelper;
import com.hulkhiretech.payments.service.helpers.ExpirePaymentHelper;
import com.hulkhiretech.payments.service.helpers.RetrievePaymentHelper;
import com.hulkhiretech.payments.service.interfaces.PaymentServiceInterface;
import com.hulkhiretech.payments.service.validation.Validation;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentServiceInterface
{
	private final HttpServiceEngine httpServiceEngine;
	
	private final CreatePaymentHelper createPaymentHelper;
	
	private final RetrievePaymentHelper retrievePaymentHelper;
	
	private final ExpirePaymentHelper expirePaymentHelper;
	
	@Override
	public ResponseCreatePayment createPayment(RequestCreatePayment paymentRequest)
	{
		Validation.Validate_CreatePayment(paymentRequest);
		
		log.info("Creating payment for request {}", paymentRequest);
		final var request = createPaymentHelper.PrepareRequest(paymentRequest);
		final var res = httpServiceEngine.MakeRequest(request);
		return createPaymentHelper.ProcessResponse(res);
	}
	
	@Override
	public ResponseRetrievePayment retrievePayment(String paymentId)
	{
		Validation.Validate_PaymentId(paymentId);
		
		log.info("Retrieving payment for id {}", paymentId);
		final var request = retrievePaymentHelper.PrepareRequest(paymentId);
		final var res = httpServiceEngine.MakeRequest(request);
		return retrievePaymentHelper.ProcessResponse(res);
	}
	
	@Override
	public ResponseExpirePayment expirePayment(String paymentId)
	{
		Validation.Validate_PaymentId(paymentId);
		
		log.info("Expiring payment for id {}", paymentId);
		final var request = expirePaymentHelper.PrepareRequest(paymentId);
		final var res = httpServiceEngine.MakeRequest(request);
		return expirePaymentHelper.ProcessResponse(res);
	}
}
