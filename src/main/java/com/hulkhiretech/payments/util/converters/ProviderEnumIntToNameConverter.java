package com.hulkhiretech.payments.util.converters;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.enums.ProviderEnum;

public class ProviderEnumIntToNameConverter extends AbstractConverter<Integer, String>
{
	@Override
	protected String convert(Integer source)
	{
		return ProviderEnum.fromId(source).getName();
	}
}
