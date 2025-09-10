package com.hulkhiretech.payments.exception;

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
	
	public CustomProviderException(String errorCode, List<String> errorMessages, HttpStatus httpStatus)
	{
		super(String.format("Error Code: %s, Messages: %s", errorCode, String.join(", ", errorMessages)));
		this.errorCode = errorCode;
		this.errorMessages = errorMessages;
		this.httpStatus = httpStatus;
	}
	public CustomProviderException(String errorCode, String errorMessage, HttpStatus httpStatus)
	{
		super(String.format("Error Code: %s, Messages: %s", errorCode, errorMessage));
		this.errorCode = errorCode;
		this.errorMessages = List.of(errorMessage);
		this.httpStatus = httpStatus;
	}
	
	private final String errorCode;
	private final List<String> errorMessages;
	private final HttpStatus httpStatus;
}
