package com.hulkhiretech.payments.controller.pojo;

import java.util.List;

import lombok.Data;

@Data
public class SessionCreateRequest
{
    private String currency;
	private String successUrl;
	private String cancelUrl;
	private List<ProductRequest> products;
	private List<String> paymentMethods;
}
