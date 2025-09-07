package com.hulkhiretech.payments.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class CustomProviderException extends RuntimeException
{
	@java.io.Serial
    static final long serialVersionUID = -7034897190785266939L;
	
	private final List<String> errorMessages;
	private final HttpStatus httpStatus;
}
