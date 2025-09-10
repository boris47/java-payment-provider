package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.TxnResponse;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;

public interface PaymentServiceInterface
{
	public TxnResponse createTxn(CreateTxnRequest request);
	
	public TxnResponse initiateTxn(String txnReference, InitiateTxnRequest request);
}
