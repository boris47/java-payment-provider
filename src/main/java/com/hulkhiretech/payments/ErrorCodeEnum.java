package com.hulkhiretech.payments;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum
{
	GENERIC_ERROR("30000", "Unable to precess the request at this time, try again later"),
	UNABLE_TO_CONNECT("30001", "Unable to connect to payment provider"),
	UNABLE_TO_INITIATE_PAYMENT("30002", "Unable to initiate payment with payment provider")
	
	;
	
	private final String errorCode;
	private final String errorMessage;
	
	private ErrorCodeEnum(String errorCode, String errorMessage)
	{
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String GetIndexedMessage(int idx)
	{
		return String.format("Item (%d) -> %s", idx, errorMessage);
	}
}
