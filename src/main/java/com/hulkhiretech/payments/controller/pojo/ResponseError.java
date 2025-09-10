package com.hulkhiretech.payments.controller.pojo;

import java.util.List;

import lombok.Getter;

@Getter
public class ResponseError
{
	private final String errorCode;
	private final List<String> errorMessages;
	
	public ResponseError(String errorCode, List<String> errorMessages)
	{
		this.errorCode = errorCode;
		this.errorMessages = errorMessages;
	}
}
