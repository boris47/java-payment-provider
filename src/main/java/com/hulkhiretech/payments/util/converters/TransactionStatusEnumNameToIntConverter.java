package com.hulkhiretech.payments.util.converters;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.TransactionStatusEnum;

public class TransactionStatusEnumNameToIntConverter extends AbstractConverter<String, Integer>
{
	@Override
	protected Integer convert(String source)
	{
		return TransactionStatusEnum.fromName(source).getId();
	}
}
