package com.hulkhiretech.payments.pojo;

import lombok.Data;

@Data
public class StripeProviderPaymentResponse
{
	private String id;
	private String paymentUrl;
}
