package com.hulkhiretech.payments.service.processors;

import com.hulkhiretech.payments.entity.TransactionDTO;
import com.hulkhiretech.payments.service.interfaces.TxnStatusProcessor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendingStatusProcessor implements TxnStatusProcessor
{
	@Override
	public TransactionDTO processStatus(TransactionDTO txn)
	{
		log.info("Processing transaction in PendingStatusProcessor");
		
		// TODO Updae DB as Pending, and set paymetreference id from stripe response
		
		return txn;
	}
}