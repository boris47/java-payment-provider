package com.hulkhiretech.payments.__alt.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import lombok.Data;

@Data
public class HttpRequest
{
	private HttpMethod method;
	private String url;
	private HttpHeaders headers; 
	private Object body = ""; // Necessary since RestTemplate does not accept null body
}
