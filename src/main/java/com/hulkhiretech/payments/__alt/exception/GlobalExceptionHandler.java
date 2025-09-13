package com.hulkhiretech.payments.__alt.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hulkhiretech.payments.__alt.ErrorCodeEnum;
import com.hulkhiretech.payments.__alt.controller.pojo.ResponseError;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
	@ExceptionHandler(CustomProviderException.class)
	public ResponseEntity<?> handleStripeProviderException(CustomProviderException ex)
	{
		ResponseError errorResponse = new ResponseError(ex.getErrorMessages());
		
		return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleRuntimeException(RuntimeException ex)
	{
		ResponseError errorResponse = new ResponseError(
			List.of(ErrorCodeEnum.GENERIC_ERROR.getErrorMessage())
		);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

