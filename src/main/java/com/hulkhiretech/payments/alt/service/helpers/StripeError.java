package com.hulkhiretech.payments.alt.service.helpers;

import lombok.Getter;


@Getter
public class StripeError
{
	private StripeErrorType type;
	private String message;
}
