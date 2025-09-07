package com.hulkhiretech.payments.controller.pojo;

import lombok.Data;

@Data
public class RequestCreatePayment_LineItem
{
	private String currency;
	private int quantity;
	private String productName;
	private int unitAmount;
}
