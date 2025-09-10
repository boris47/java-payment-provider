package com.hulkhiretech.payments.util.converters;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.ProviderEnum;

public class ProviderEnumNameToIntConverter extends AbstractConverter<String, Integer>
{
	@Override
	protected Integer convert(String source)
	{
		return ProviderEnum.fromName(source).getId();
	}
}
