package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;
import com.hulkhiretech.payments.pojo.TxnResponse;

public interface PaymentServiceInterface
{
	public TxnResponse createTxn(CreateTxnRequest request);
	
	public TxnResponse initiateTxn(String txnReference, InitiateTxnRequest request);

	public TxnResponse successTxn(String txnReference);

	public TxnResponse failedTxn(String txnReference);
}
