package com.hulkhiretech.payments.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.CreateTxnResponse;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;
import com.hulkhiretech.payments.service.interfaces.PaymentServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController
{
	private final PaymentServiceInterface paymentService;
	
	@PostMapping
	public CreateTxnResponse createTxn(@RequestBody CreateTxnRequest request)
	{
		log.info("Creating transaction with request: {}", request);
		
		final var response = paymentService.createTxn(request);
		log.info("Response from service layer: {}", response);
		
		return response;
	}
	
	@PostMapping("{id}/initiate")
	public String initiateTxn(@PathVariable String id, @RequestBody InitiateTxnRequest request)
	{
		log.info("Initiating transaction with request: {}", request);
		
		String response = paymentService.initiateTxn(id, request);
		
		log.info("Response from service layer: {}", response);
		
		return response;
	}
}
