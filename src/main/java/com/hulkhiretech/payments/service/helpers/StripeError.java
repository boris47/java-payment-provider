package com.hulkhiretech.payments.service.helpers;

import lombok.Getter;


@Getter
public class StripeError
{
	private StripeErrorType type;
	private String message;
}
