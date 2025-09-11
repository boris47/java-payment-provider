package com.hulkhiretech.payments.alt.service.helpers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.alt.ErrorCodeEnum;
import com.hulkhiretech.payments.alt.exception.CustomProviderException;
import com.hulkhiretech.payments.util.JsonUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SimpleResponseProcessor
{
	private final JsonUtil jsonUtil;
	
	public <T> T ProcessResponse(ResponseEntity<String> response, Class<T> outClass, ErrorCodeEnum errorCode)
	{
		final var statusCode = response.getStatusCode();
		if (statusCode.is4xxClientError() || statusCode.is5xxServerError())
		{
			log.error("Stripe returned error code {} with body {}", statusCode.value(), response.getBody());
			String err = ErrorCodeEnum.STRIPE_ERROR.getErrorMessage();
			try {
				err = jsonUtil.ConvertJsonToObject(response.getBody(), StripeErrorWrapper.class)
					.getError()
					.getMessage();
			} catch (Exception e) {}
			
			throw new CustomProviderException
			(
				List.of(err),
				HttpStatus.valueOf(response.getStatusCode().value())
			);
		}
		
		final var converted = jsonUtil.ConvertJsonToObject(response.getBody(), outClass);
		if (converted == null)
		{
			log.error("Failed to convert response {} to class {}", response.getBody(), outClass.getName());
			throw new CustomProviderException
			(
				List.of(errorCode.getErrorMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
		
		return converted;
	}
}
