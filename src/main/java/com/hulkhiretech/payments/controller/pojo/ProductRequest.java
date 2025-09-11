package com.hulkhiretech.payments.controller.pojo;

import lombok.Data;

@Data
public class ProductRequest
{
	private String name;
	private Long price; // cents
	private Long quantity;
}
