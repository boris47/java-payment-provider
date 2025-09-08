package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.entity.TransactionDTO;

public interface PaymentStatusServiceInterface
{
	public TransactionDTO processStatus(TransactionDTO txn);
}
