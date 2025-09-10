package com.hulkhiretech.payments.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JsonUtil
{
	private final ObjectMapper objectMapper;
	
	public <T> T ConvertJsonToObject(String input, Class<T> outClass)
	{
		T response = null;
		try
		{
			response = objectMapper.readValue(input, outClass);
		}
		catch (Exception e) // TODO
		{
			log.error("Error converting JSON to Object {}", e.getMessage());
			throw new RuntimeException("JOSN to Object conversion failed", e);
		}
		return response;
	}
	
	public String ConvertObjectToJson(Object input)
	{
		String response = null;
		try
		{
			response = objectMapper.writeValueAsString(input);
		}
		catch (Exception e) // TODO
		{
			log.error("Error converting Object to JSON {}", e.getMessage());
			// throw new RuntimeException("Object to JSON conversion failed", e);
		}
		return response;
	}
}
