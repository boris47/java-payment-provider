package com.hulkhiretech.payments.util.converters;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.TransactionStatusEnum;

public class TransactionStatusEnumIntToNameConverter extends AbstractConverter<Integer, String>
{
	@Override
	protected String convert(Integer source)
	{
		return TransactionStatusEnum.fromId(source).getName();
	}
}
