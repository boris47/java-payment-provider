package com.hulkhiretech.payments.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.service.interfaces.IProcessingNotifier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingServiceNotifier implements IProcessingNotifier
{
	private final HttpServiceEngine httpServiceEngine;
	
	@Value("${stripe.processing.service.url}")
	private String processingServiceHost;
	
	@Value("${stripe.processing.service.port}")
	private String processingServicePort;
	
	@Value("${stripe.processing.service.path}")
	private String processingServicePath;
	
	@Value("${stripe.processing.service.success}")
	private String processingServiceSuccess;
	
	@Value("${stripe.processing.service.failed}")
	private String processingServiceFailed;
	
	@Override
	public void notifySuccess(String txnReference)
	{
		String finalURL = GetFinalURL(processingServiceHost, processingServicePort, processingServicePath, processingServiceSuccess, txnReference);
		
		log.info("Notifying success to processing service: url='{}', txnReference='{}'",
			finalURL,
			txnReference
		);
		
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.POST);
		request.setUrl(finalURL);
		httpServiceEngine.MakeRequest(request);
		
		log.info("Notified success to processing service: url='{}', txnReference='{}'",
			finalURL,
			txnReference
		);
	}
	
	@Override
	public void notifyFailure(String txnReference)
	{
		String finalURL = GetFinalURL(processingServiceHost, processingServicePort, processingServicePath, processingServiceFailed, txnReference);
		
		log.info("Notifying failure to processing service: url='{}', txnReference='{}'",
			finalURL,
			txnReference
		);
		
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.POST);
		request.setUrl(finalURL);
		httpServiceEngine.MakeRequest(request);
		
		log.info("Notified failure to processing service: url='{}', txnReference='{}'",
			finalURL,
			txnReference
		);
	}


	private static final String GetFinalURL(String host, String port, String path, String action, String txnReference)
	{
		List<String> parts = List.of(path, txnReference, action);
		
		StringBuilder sb = new StringBuilder();
		sb.append(host); sb.append(':'); sb.append(port);
		
		for (String part : parts)
		{
			if (part != null && !part.isBlank())
			{
				if (!part.startsWith("/"))
				{
					sb.append("/");
				}
				sb.append(part);
			}
		}

		return sb.toString();
	}
}
