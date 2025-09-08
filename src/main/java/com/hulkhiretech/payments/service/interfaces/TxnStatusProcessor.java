package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.entity.TransactionDTO;

public interface TxnStatusProcessor
{
	public TransactionDTO processStatus(TransactionDTO txn);
}
