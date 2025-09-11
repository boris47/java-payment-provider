package com.hulkhiretech.payments.controller;

import com.hulkhiretech.payments.controller.pojo.SessionCreateRequest;
import com.hulkhiretech.payments.controller.pojo.SessionResponse;
import com.hulkhiretech.payments.service.interfaces.ISessionService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/session")
@RequiredArgsConstructor
public class SessionController
{
	private final ISessionService sessionService;

	@Value("${stripe.api.key}")
	private String kApiKey;
	
	@PostConstruct
	private void OnInit()
	{
		Stripe.apiKey = kApiKey;
	}

	@PostMapping("/create")
	public SessionResponse createSession(@RequestBody SessionCreateRequest request) throws StripeException
	{
		return sessionService.createSession(request);
	}
	
	@GetMapping("/retrieve/{sessionId}")
	public SessionResponse retrieveSession(@PathVariable String sessionId) throws StripeException
	{
		return sessionService.retrieveSession(sessionId);
	}
	
	// Expire a session
	@PostMapping("/expire/{sessionId}")
	public SessionResponse expireSession(@PathVariable String sessionId) throws StripeException
	{
		return sessionService.expireSession(sessionId);
	}
	
}