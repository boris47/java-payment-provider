package com.hulkhiretech.payments.util.converters;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentMethodEnum;

public class PaymentMethodEnumNameToIntConverter extends AbstractConverter<String, Integer>
{
	@Override
	protected Integer convert(String source)
	{
		return PaymentMethodEnum.fromName(source).getId();
	}
}
