package com.hulkhiretech.payments.alt.service.interfaces;

import com.hulkhiretech.payments.alt.controller.pojo.RequestCreatePayment;
import com.hulkhiretech.payments.alt.controller.pojo.ResponseCreatePayment;
import com.hulkhiretech.payments.alt.controller.pojo.ResponseExpirePayment;
import com.hulkhiretech.payments.alt.controller.pojo.ResponseRetrievePayment;

public interface PaymentServiceInterface
{
	public ResponseCreatePayment createPayment(RequestCreatePayment request);
	
	public ResponseRetrievePayment retrievePayment(String paymentId);
	
	public ResponseExpirePayment expirePayment(String paymentId);
}
