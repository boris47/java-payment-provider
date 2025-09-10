package com.hulkhiretech.payments.util.converters;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.PaymentTypeEnum;

public class PaymentTypeEnumIntToNameConverter extends AbstractConverter<Integer, String>
{
	@Override
	protected String convert(Integer source)
	{
		return PaymentTypeEnum.fromId(source).getName();
	}
}
