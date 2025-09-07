package com.hulkhiretech.payments.http;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;

import lombok.Data;

@Data
public class HttpRequest
{
	private HttpMethod method;
	private String url;
	private HttpHeaders headers; 
	private Object body = ""; // Necessary since RestTemplate does not accept null body
}
