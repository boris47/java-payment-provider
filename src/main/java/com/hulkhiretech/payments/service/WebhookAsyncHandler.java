package com.hulkhiretech.payments.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebhookAsyncHandler
{
	@Async
	public void processEvent()
	{
		// TODO: Implement the asynchronous processing of the webhook event
	}
}
