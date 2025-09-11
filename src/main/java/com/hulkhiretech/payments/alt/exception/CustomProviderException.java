package com.hulkhiretech.payments.alt.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CustomProviderException extends RuntimeException
{
	@java.io.Serial
    static final long serialVersionUID = -7034897190785266939L;
	
	private final List<String> errorMessages;
	private final HttpStatus httpStatus;
	
	public CustomProviderException(String errorMessage, HttpStatus httpStatus)
	{
		errorMessages = List.of(errorMessage);
		this.httpStatus = httpStatus;
	}
	public CustomProviderException(List<String> errorMessages, HttpStatus httpStatus)
	{
		this.errorMessages = errorMessages;
		this.httpStatus = httpStatus;
	}
}
