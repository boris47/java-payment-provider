package com.hulkhiretech.payments.dao.interfaces;

import com.hulkhiretech.payments.entity.TransactionEntity;

public interface TransactionDao
{
	public Integer insertTransaction(TransactionEntity entity);
}
