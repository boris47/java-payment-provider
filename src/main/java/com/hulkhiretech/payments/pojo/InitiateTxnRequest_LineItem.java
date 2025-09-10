package com.hulkhiretech.payments.pojo;

import lombok.Data;

@Data
public class InitiateTxnRequest_LineItem
{
	private String currency;
	private int quantity;
	private String productName;
	private int unitAmount;
}
