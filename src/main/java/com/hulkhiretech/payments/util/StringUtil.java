package com.hulkhiretech.payments.util;

public class StringUtil
{
	private StringUtil() {/* private constructor to prevent instantiation */}
	
	public static boolean IsNullOrEmpty(String input)
	{
		return input == null || input.length() == 0;
	}
}
