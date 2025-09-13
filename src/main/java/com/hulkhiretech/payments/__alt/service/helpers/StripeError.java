package com.hulkhiretech.payments.__alt.service.helpers;

import lombok.Getter;


@Getter
public class StripeError
{
	private StripeErrorType type;
	private String message;
}
