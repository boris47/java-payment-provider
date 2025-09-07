package com.hulkhiretech.payments.controller.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseRetrievePayment
{
	private String id;
	
	@JsonProperty("payment_status")
	private String paymentStatus;
	
	private String status;
}
