package com.hulkhiretech.payments.alt.service.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.alt.ErrorCodeEnum;
import com.hulkhiretech.payments.alt.http.HttpRequest;
import com.hulkhiretech.payments.alt.controller.pojo.ResponseExpirePayment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpirePaymentHelper
{
	// Don't use final along with Value Annotation !!
	@Value("${stripe.api.key}")
	private String kKey;
	
	@Value("${stripe.session.expire.url}")
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
			request.setMethod(HttpMethod.POST);
			request.setUrl(kEndpoint.replace("{id}", paymentId));
			request.setHeaders(headers);
		}
		
		return request;
	}
	
	public ResponseExpirePayment ProcessResponse(ResponseEntity<String> response)
	{
		return simpleResponseProcessor.ProcessResponse(
			response,
			ResponseExpirePayment.class,
			ErrorCodeEnum.FAILED_PROCESS_EXPIRE_PAYMENT_RESPONSE
		);
	}
}
