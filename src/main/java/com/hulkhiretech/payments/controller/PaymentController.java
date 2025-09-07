package com.hulkhiretech.payments.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.controller.pojo.RequestCreatePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseCreatePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseExpirePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseRetrievePayment;
import com.hulkhiretech.payments.service.interfaces.PaymentServiceInterface;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController
{
	private final PaymentServiceInterface paymentInterface;
	
	@PostConstruct
	private void OnInit()
	{
	//	log.info("PaymentService {} create in {}", paymentInterface.getClass().getName(), PaymentController.class.getName());
	}
	
	@PostMapping()
	public ResponseCreatePayment createPayment(@RequestBody RequestCreatePayment request)
	{
		return paymentInterface.createPayment(request);
	}
	
	@GetMapping("/{paymentId}")
	public ResponseRetrievePayment retrievePayment(@PathVariable String paymentId)
	{
		return paymentInterface.retrievePayment(paymentId);
	}
	
	@PostMapping("/{paymentId}/expire")
	public ResponseExpirePayment expirePayment(@PathVariable String paymentId)
	{
		return paymentInterface.expirePayment(paymentId);
	}
}
