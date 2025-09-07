package com.hulkhiretech.payments.controller.pojo;

import lombok.Data;

@Data
public class ResponseExpirePayment
{
	private String id;
	private String payment_status;
	private String status;
}
