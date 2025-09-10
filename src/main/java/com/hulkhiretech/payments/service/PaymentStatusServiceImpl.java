package com.hulkhiretech.payments.service;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.entity.TransactionDTO;
import com.hulkhiretech.payments.enums.TransactionStatusEnum;
import com.hulkhiretech.payments.service.interfaces.PaymentStatusServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentStatusServiceImpl implements PaymentStatusServiceInterface
{
	private final PaymentStatusFactory paymentStatusFactory;
	
	@Override
	public TransactionDTO processStatus(TransactionDTO txn)
	{
		log.info("Processing payment status for txnStatus: " + txn.getTxnStatus());
		
		final var statusEnum = TransactionStatusEnum.fromName(txn.getTxnStatus());
		final var processor = paymentStatusFactory.getProcessor(statusEnum);
		if (processor == null)
		{
			log.error("No processor found for txnStatusId: " + txn.getTxnStatus());
			throw new IllegalArgumentException("Invalid transaction status ID: " + txn.getTxnStatus());
		}
		
		log.info("Using processor: " + processor.getClass().getSimpleName());
		return processor.processStatus(txn);
	}

}
