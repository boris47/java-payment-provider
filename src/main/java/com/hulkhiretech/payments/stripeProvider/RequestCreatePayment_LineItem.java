package com.hulkhiretech.payments.stripeProvider;

import lombok.Data;

@Data
public class RequestCreatePayment_LineItem
{
	private String currency;
	private int quantity;
	private String productName;
	private int unitAmount;
}
