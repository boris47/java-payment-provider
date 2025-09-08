package com.hulkhiretech.payments.util;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentMethodEnum;

public class PaymentMethodEnumConverter extends AbstractConverter<String, Integer>
{
	@Override
	protected Integer convert(String source)
	{
		return PaymentMethodEnum.fromName(source).getId();
	}
}
