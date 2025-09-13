package com.hulkhiretech.payments.__alt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulkhiretech.payments.__alt.controller.pojo.RequestCreatePayment;
import com.hulkhiretech.payments.__alt.controller.pojo.ResponseCreatePayment;
import com.hulkhiretech.payments.__alt.controller.pojo.ResponseExpirePayment;
import com.hulkhiretech.payments.__alt.controller.pojo.ResponseRetrievePayment;
import com.hulkhiretech.payments.__alt.service.interfaces.PaymentServiceInterface;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController
{
	private final PaymentServiceInterface paymentInterface;
	
	@PostConstruct
	private void OnInit()
	{
		log.info("Initialized PaymentController");
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
