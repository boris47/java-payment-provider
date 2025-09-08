package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.CreateTxnResponse;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;

public interface PaymentServiceInterface
{
	public CreateTxnResponse createTxn(CreateTxnRequest request);
	
	public String initiateTxn(String id, InitiateTxnRequest request);
}
