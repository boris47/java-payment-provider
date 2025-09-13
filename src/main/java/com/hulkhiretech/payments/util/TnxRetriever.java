package com.hulkhiretech.payments.util;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class TnxRetriever
{
	public static String GetTransactionId(String url)
	{
		// parse the url and retrieve the value for 'tid' query parameter
		if (url == null || url.isBlank())
		{
			return null;
		}
		
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(url).build();
		String tid = uriComponents.getQueryParams().getFirst("tid");
		return tid;
	}
}
