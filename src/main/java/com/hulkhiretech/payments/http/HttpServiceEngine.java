package com.hulkhiretech.payments.http;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.hulkhiretech.payments.ErrorCodeEnum;
import com.hulkhiretech.payments.exception.CustomProviderException;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Component
@Slf4j
public class HttpServiceEngine
{
	private RestClient restClient;
	
	public HttpServiceEngine(RestClient.Builder restClientBuilder)
	{
		this.restClient = restClientBuilder.build();
	}
	
	public ResponseEntity<String> MakeRequest(HttpRequest httpRequest)
	{
		try
		{
			return this.restClient
				.method(httpRequest.getMethod())
				.uri(httpRequest.getUrl())
				.headers(h -> h.addAll(httpRequest.getHeaders()))
				.body(httpRequest.getBody())
				.retrieve()
				.toEntity(String.class)
			;
		}
		catch (HttpClientErrorException | HttpServerErrorException ex) // Valid failure
		{
			if (ex.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE // 503
			||  ex.getStatusCode() == HttpStatus.GATEWAY_TIMEOUT     // 504
			) {
				throw new CustomProviderException(
					List.of(ErrorCodeEnum.UNABLE_TO_CONNECT.getErrorMessage(), httpRequest.getUrl()),
					HttpStatus.SERVICE_UNAVAILABLE
				);
			}
			
			return ResponseEntity.status(ex.getStatusCode())
				.body(ex.getResponseBodyAsString());
		}
		catch (RuntimeException ex) // critical failure
		{
			log.error("Critical error while making HTTP request", ex);
			
			throw new CustomProviderException(
				List.of(ErrorCodeEnum.UNABLE_TO_CONNECT.getErrorMessage(), httpRequest.getUrl()),
				HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
	}
}
