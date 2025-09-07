package com.hulkhiretech.payments.service.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.ErrorCodeEnum;
import com.hulkhiretech.payments.controller.pojo.ResponseRetrievePayment;
import com.hulkhiretech.payments.http.HttpRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RetrievePaymentHelper
{
	// Don't use final along with Value Annotation !!
	@Value("${stripe.api.key}")
	private String kKey;
	
	@Value("${stripe.session.retrieve.url}")
	private String kEndpoint;
	
	private final SimpleResponseProcessor simpleResponseProcessor;
	
	public HttpRequest PrepareRequest(String paymentId)
	{
		final HttpHeaders headers = new HttpHeaders();
		{
			headers.setBasicAuth(kKey, "");
		}
		
		final HttpRequest request = new HttpRequest();
		{
			request.setMethod(HttpMethod.GET);
			request.setUrl(kEndpoint.replace("{id}", paymentId));
			request.setHeaders(headers);
		}
		
		return request;
	}
	
	public ResponseRetrievePayment ProcessResponse(ResponseEntity<String> response)
	{
		return simpleResponseProcessor.ProcessResponse(
			response,
			ResponseRetrievePayment.class,
			ErrorCodeEnum.FAILED_PROCESS_RETRIEVE_PAYMENT_RESPONSE
		);
	}
}
