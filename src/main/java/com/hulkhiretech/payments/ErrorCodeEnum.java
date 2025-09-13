package com.hulkhiretech.payments;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum
{
	GENERIC_ERROR("Unable to precess the request at this time, try again later"),
	UNABLE_TO_CONNECT("Unable to connect to stripe system"),
	STRIPE_ERROR("Stripe Generic error"),
	
	INVALID_STRIPE_SIGNATURE("Invalid stripe signature"),
	
	FAILED_PROCESS_CREATE_PAYMENT_RESPONSE("Failed to process create payment response"),
	FAILED_PROCESS_RETRIEVE_PAYMENT_RESPONSE("Failed to process retrieve payment response"),
	FAILED_PROCESS_EXPIRE_PAYMENT_RESPONSE("Failed to process expire payment response"),
	
	INVALID_ITEMS("Invalid items"),
	INVALID_CURRENCY("Invalid currency in request"),
	INVALID_QUANTITY("Invalid quantity in request"),
	INVALID_PRODUCT_NAME("Invalid product name in request"),
	INVALID_UNIT_AMOUNT("Invalid unity amount in request"),
	
	;
	
	private final String errorMessage;
	
	private ErrorCodeEnum(String errorMessage)
	{
		this.errorMessage = errorMessage;
	}
	
	public String GetIndexedMessage(int idx)
	{
		return String.format("Item (%d) -> %s", idx, errorMessage);
	}
}
