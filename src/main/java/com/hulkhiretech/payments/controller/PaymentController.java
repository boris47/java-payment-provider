package com.hulkhiretech.payments.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;
import com.hulkhiretech.payments.pojo.TxnResponse;
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
	public TxnResponse createTxn(@RequestBody CreateTxnRequest request)
	{
		log.info("Creating transaction with request: {}", request);
		
		final var response = paymentService.createTxn(request);
		
		log.info("Response from service layer: {}", response);
		
		return response;
	}
	
	@PostMapping("/{txnReference}/initiate")
	public TxnResponse initiateTxn(@PathVariable String txnReference, @RequestBody InitiateTxnRequest request)
	{
		log.info("Initiating transaction with request: {}", request);
		
		final var response = paymentService.initiateTxn(txnReference, request);
		
		log.info("Response from service layer: {}", response);
		
		return response;
	}
	
	@PostMapping("/{txnReference}/success")
	public TxnResponse successTxn(@PathVariable String txnReference)
	{
		log.info("Success transaction with reference: {}", txnReference);

		final var response = paymentService.successTxn(txnReference);
		
		return response;
	}
	
	@PostMapping("/{txnReference}/failed")
	public TxnResponse failedTxn(@PathVariable String txnReference)
	{
		log.info("Failing transaction with reference: {}", txnReference);

		final var response = paymentService.failedTxn(txnReference);
		
		return response;
	}
}
