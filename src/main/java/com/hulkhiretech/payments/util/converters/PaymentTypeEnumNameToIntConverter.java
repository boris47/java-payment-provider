package com.hulkhiretech.payments.util.converters;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentTypeEnum;

public class PaymentTypeEnumNameToIntConverter extends AbstractConverter<String, Integer>
{
	@Override
	protected Integer convert(String source)
	{
		return PaymentTypeEnum.fromName(source).getId();
	}
}
