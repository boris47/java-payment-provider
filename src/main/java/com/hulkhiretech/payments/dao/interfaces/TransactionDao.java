package com.hulkhiretech.payments.dao.interfaces;

import com.hulkhiretech.payments.entity.TransactionEntity;

public interface TransactionDao
{
	public Integer insertTransaction(TransactionEntity entity);
	
	public TransactionEntity getTransactionByReference(String txnReference);
	
	public Integer UpdateTransactionDetailsByReference(TransactionEntity entity);
}
