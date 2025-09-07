package com.hulkhiretech.payments.service.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.hulkhiretech.payments.ErrorCodeEnum;
import com.hulkhiretech.payments.controller.pojo.RequestCreatePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseCreatePayment;
import com.hulkhiretech.payments.http.HttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreatePaymentHelper
{
	// Don't use final along with Value Annotation !!
	@Value("${stripe.api.key}")
	private String kKey;
	
	@Value("${stripe.session.create.url}")
	private String kEndpoint;
	
	private final SimpleResponseProcessor simpleResponseProcessor;
	
	public HttpRequest PrepareRequest(RequestCreatePayment paymentRequest)
	{
		final HttpHeaders headers = new HttpHeaders();
		{
			headers.setBasicAuth(kKey, "");
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		}
		
		final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
		{
			requestBody.add("mode", "payment");
			requestBody.add("success_url", paymentRequest.getSuccessUrl());
			requestBody.add("cancel_url", paymentRequest.getCancelUrl());
			requestBody.add("payment_method_types[]", paymentRequest.getPaymentMethod());
			
			final var items = paymentRequest.getLineItems();
			for (int i = 0, len = items.size(); i < len; ++i)
			{
				final var item = items.get(i);
				requestBody.add(String.format("line_items[%d][price_data][product_data][name]", i), item.getProductName());
				requestBody.add(String.format("line_items[%d][price_data][unit_amount]", i), String.valueOf(item.getUnitAmount()));
				requestBody.add(String.format("line_items[%d][price_data][currency]", i), item.getCurrency());
				requestBody.add(String.format("line_items[%d][quantity]", i), String.valueOf(item.getQuantity()));
			}
		}
		
		final HttpRequest request = new HttpRequest();
		{
			request.setMethod(HttpMethod.POST);
			request.setUrl(kEndpoint);
			request.setHeaders(headers);
			request.setBody(requestBody);
		}
		
		return request;
	}
	
	public ResponseCreatePayment ProcessResponse(ResponseEntity<String> response)
	{
		return simpleResponseProcessor.ProcessResponse(
			response,
			ResponseCreatePayment.class,
			ErrorCodeEnum.FAILED_PROCESS_CREATE_PAYMENT_RESPONSE
		);
	}
}
