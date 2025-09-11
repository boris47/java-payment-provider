package com.hulkhiretech.payments.alt.service.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.hulkhiretech.payments.alt.exception.CustomProviderException;
import com.hulkhiretech.payments.alt.controller.pojo.RequestCreatePayment;
import com.hulkhiretech.payments.util.StringUtil;

public class Validation
{
	private Validation() {/* private constructor to prevent instantiation */}
	
	public static void Validate_CreatePayment(RequestCreatePayment paymentRequest)
	{
		final List<String> errorMessages = new ArrayList<String>();
		if (paymentRequest.getSuccessUrl() == null || paymentRequest.getSuccessUrl().length() == 0)
		{
			errorMessages.add(ValidationErrorCodes.INVALID_SUCCCESS_URL.getErrorMessage());
		}
		
		if (paymentRequest.getCancelUrl() == null || paymentRequest.getCancelUrl().length() == 0)
		{
			errorMessages.add(ValidationErrorCodes.INVALID_CANCEL_URL.getErrorMessage());
		}
		
		if (paymentRequest.getLineItems() == null || paymentRequest.getLineItems().size() == 0)
		{
			errorMessages.add(ValidationErrorCodes.INVALID_ITEMS.getErrorMessage());
		}
		else
		{
			var items = paymentRequest.getLineItems();
			for (int i = 0, len = items.size(); i < len; ++i)
			{
				var item = items.get(i);
				
				if (StringUtil.IsNullOrEmpty(item.getCurrency()) || item.getCurrency().length() < 3)
					errorMessages.add(ValidationErrorCodes.INVALID_CURRENCY.GetIndexedMessage(i));
				if (item.getQuantity() <= 0)
					errorMessages.add(ValidationErrorCodes.INVALID_QUANTITY.GetIndexedMessage(i));
				if (StringUtil.IsNullOrEmpty(item.getProductName()))
					errorMessages.add(ValidationErrorCodes.INVALID_PRODUCT_NAME.GetIndexedMessage(i));
				if (item.getUnitAmount() <= 0)
					errorMessages.add(ValidationErrorCodes.INVALID_UNIT_AMOUNT.GetIndexedMessage(i));
			}
		}
		
		if (errorMessages.size() > 0)
		{
			throw new CustomProviderException(errorMessages, HttpStatus.BAD_REQUEST);
		}
	}

	public static void Validate_PaymentId(String paymentId)
	{
		if (StringUtil.IsNullOrEmpty(paymentId))
		{
			throw new CustomProviderException(
				List.of(ValidationErrorCodes.INVALID_PAYMENT_ID.getErrorMessage()),
				HttpStatus.BAD_REQUEST
			);
		}
	}
}
