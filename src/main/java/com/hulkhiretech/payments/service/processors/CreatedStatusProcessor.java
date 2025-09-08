package com.hulkhiretech.payments.service.processors;

import com.hulkhiretech.payments.dao.interfaces.TransactionDao;
import com.hulkhiretech.payments.entity.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;
import com.hulkhiretech.payments.service.interfaces.TxnStatusProcessor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreatedStatusProcessor implements TxnStatusProcessor
{
	private final TransactionDao transactionDao;
	
	private final ModelMapper modelMapper;
	
	@Override
	public TransactionDTO processStatus(TransactionDTO txn)
	{
		log.info("Processing transaction in CreatedStatusProcessor || txn {}", txn);
		
		final var entity = modelMapper.map(txn, TransactionEntity.class);
		final var id = transactionDao.insertTransaction(entity);
		{
			txn.setId(id);
		}
		
		log.info("After inserting transaction in CreatedStatusProcessor || txn {}", txn);
		return txn;
	}
}