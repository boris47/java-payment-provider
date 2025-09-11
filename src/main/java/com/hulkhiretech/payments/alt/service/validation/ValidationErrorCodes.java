package com.hulkhiretech.payments.alt.service.validation;

import lombok.Getter;

@Getter
public enum ValidationErrorCodes
{
	INVALID_PAYMENT_ID("Invalid payment id"),
	
	INVALID_SUCCCESS_URL("Invalid success url"),
	INVALID_CANCEL_URL("Invalid cancel url"),
	INVALID_ITEMS("Invalid items"),
	INVALID_CURRENCY("Invalid currency in request"),
	INVALID_QUANTITY("Invalid quantity in request"),
	INVALID_PRODUCT_NAME("Invalid product name in request"),
	INVALID_UNIT_AMOUNT("Invalid unity amount in request"),
	
	;
	
	private final String errorMessage;
	
	ValidationErrorCodes(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	public String GetIndexedMessage(int idx)
	{
		return String.format("Item (%d) -> %s", idx, errorMessage);
	}
}
