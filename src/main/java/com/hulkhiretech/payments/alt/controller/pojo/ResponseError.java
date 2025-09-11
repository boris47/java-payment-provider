package com.hulkhiretech.payments.alt.controller.pojo;

import java.util.List;

import lombok.Getter;

@Getter
public class ResponseError
{
	private final List<String> errorMessages;
	
	public ResponseError(List<String> errorMessages)
	{
		this.errorMessages = errorMessages;
	}
}
