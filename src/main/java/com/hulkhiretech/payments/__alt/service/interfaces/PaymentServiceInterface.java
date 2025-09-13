package com.hulkhiretech.payments.__alt.service.interfaces;

import com.hulkhiretech.payments.__alt.controller.pojo.RequestCreatePayment;
import com.hulkhiretech.payments.__alt.controller.pojo.ResponseCreatePayment;
import com.hulkhiretech.payments.__alt.controller.pojo.ResponseExpirePayment;
import com.hulkhiretech.payments.__alt.controller.pojo.ResponseRetrievePayment;

public interface PaymentServiceInterface
{
	public ResponseCreatePayment createPayment(RequestCreatePayment request);
	
	public ResponseRetrievePayment retrievePayment(String paymentId);
	
	public ResponseExpirePayment expirePayment(String paymentId);
}
