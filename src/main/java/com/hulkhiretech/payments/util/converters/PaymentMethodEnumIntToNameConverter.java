package com.hulkhiretech.payments.util.converters;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentMethodEnum;

public class PaymentMethodEnumIntToNameConverter extends AbstractConverter<Integer, String>
{
	@Override
	protected String convert(Integer source)
	{
		return PaymentMethodEnum.fromId(source).getName();
	}
}
