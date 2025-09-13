package com.hulkhiretech.payments.service.webhookProcessorsV2;

import java.util.Map;

public abstract class BaseWebhookProcessor implements IWebhookProcessor
{
	protected static <T> T getValue(Map<String, Object> map, String key, Class<T> clazz, T defaultValue)
	{
		if (map == null || key == null || clazz == null)
		{
			return defaultValue;
		}

		Object value = map.get(key);
		try
		{
			return clazz.cast(value);
		}
		catch (ClassCastException e)
		{
			return defaultValue;
		}
	}
}
