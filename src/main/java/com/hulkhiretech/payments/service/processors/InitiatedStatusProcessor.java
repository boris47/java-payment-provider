package com.hulkhiretech.payments.service.processors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.dao.interfaces.TransactionDao;
import com.hulkhiretech.payments.entity.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;
import com.hulkhiretech.payments.service.interfaces.TxnStatusProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InitiatedStatusProcessor implements TxnStatusProcessor
{
	private final TransactionDao transactionDao;
	
	private final ModelMapper modelMapper;
	
	@Override
	public TransactionDTO processStatus(TransactionDTO txn)
	{
		log.info("Processing transaction in InitiatedStatusProcessor || txn {}", txn);
		
		final var entity = modelMapper.map(txn, TransactionEntity.class);
		
		final var res = transactionDao.UpdateTransactionDetailsByReference(entity);
		
		log.info("Updated rows in InitiatedStatusProcessor: {}", res);
		
		return txn;
	}
}