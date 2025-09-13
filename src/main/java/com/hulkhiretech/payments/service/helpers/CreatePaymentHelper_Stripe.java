package com.hulkhiretech.payments.service.helpers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.ErrorCodeEnum;
import com.hulkhiretech.payments.exception.CustomProviderException;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;
import com.hulkhiretech.payments.pojo.StripeProviderPaymentResponse;
import com.hulkhiretech.payments.stripeProvider.RequestCreatePayment;
import com.hulkhiretech.payments.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreatePaymentHelper_Stripe
{
	@Value("${stripe.provider.service.url}")
	private String kStripeProviderServiceUrl;
	
	@Value("${stripe.provider.service.session.create.path}")
	private String kCreatePath;
	
	
	
	private final ModelMapper modelMapper;
	
	private final JsonUtil jsonUtil;
	
	
	public HttpRequest PrepareHttpReq(InitiateTxnRequest request, String txnReference)
	{
		log.info("Preparing HttpRequest for Stripe Create Payment {}", request);
		
		// convert from InitiateTxnRequest to RequestCreatePayment
		final var reqCreatePayment = modelMapper.map(request, RequestCreatePayment.class);
		
		// Append the txnReference to success and cancel URL
		{
			reqCreatePayment.setClientReferenceId(txnReference);
		}
		
		String reqAsJson = jsonUtil.ConvertObjectToJson(reqCreatePayment);
		if (reqAsJson == null)
		{
			throw new CustomProviderException(
				ErrorCodeEnum.UNABLE_TO_INITIATE_PAYMENT.getErrorCode(),
				ErrorCodeEnum.UNABLE_TO_INITIATE_PAYMENT.getErrorMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
		log.info("Converted request body to JSON: {}", reqAsJson);
		
		final HttpHeaders headers = new HttpHeaders();
		{
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		
		final var finalUrl = kStripeProviderServiceUrl + kCreatePath;
		log.info("Final URL for stripe-provider-service create payment: {}", finalUrl);
		
		HttpRequest req = HttpRequest.builder()
			.method(org.springframework.http.HttpMethod.POST)
			.url(finalUrl)
			.headers(headers)
			.body(reqAsJson)    // TODO
			.build();
		
		return req;
	}

	public StripeProviderPaymentResponse ProcessResponse(ResponseEntity<String> res)
	{
		if (!res.getStatusCode().is2xxSuccessful())
		{
			// TOOD
			return null;
		}
		
		// convert to obj and check url
		var obj = jsonUtil.ConvertJsonToObject(res.getBody(), StripeProviderPaymentResponse.class);
		if (obj == null || obj.getPaymentUrl() == null || obj.getPaymentUrl().isBlank())
		{
			throw new CustomProviderException(
				ErrorCodeEnum.UNABLE_TO_INITIATE_PAYMENT.getErrorCode(),
				ErrorCodeEnum.UNABLE_TO_INITIATE_PAYMENT.getErrorMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
		
		return obj;
	}
}
