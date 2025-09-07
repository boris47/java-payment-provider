package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.controller.pojo.RequestCreatePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseCreatePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseExpirePayment;
import com.hulkhiretech.payments.controller.pojo.ResponseRetrievePayment;

public interface PaymentServiceInterface
{
	public ResponseCreatePayment createPayment(RequestCreatePayment request);
	
	public ResponseRetrievePayment retrievePayment(String paymentId);
	
	public ResponseExpirePayment expirePayment(String paymentId);
}
