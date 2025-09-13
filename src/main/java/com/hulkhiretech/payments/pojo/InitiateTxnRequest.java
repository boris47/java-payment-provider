package com.hulkhiretech.payments.pojo;

import java.util.List;

import lombok.Data;

@Data
public class InitiateTxnRequest
{
	private String currency;
	private String successUrl;
	private String cancelUrl;
	private List<String> paymentMethods;
	
	private List<Product> products;
	
	@Data
	private static class Product
	{
		private String productName;
		private Long unitAmount; // cents
		private Long quantity;
	}
}
