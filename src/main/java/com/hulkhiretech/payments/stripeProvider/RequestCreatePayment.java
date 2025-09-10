package com.hulkhiretech.payments.stripeProvider;

import java.util.List;

import lombok.Data;

@Data
public class RequestCreatePayment
{
	private String successUrl;
	private String cancelUrl;
	private String paymentMethod;
	
	private List<RequestCreatePayment_LineItem> lineItems;
}
