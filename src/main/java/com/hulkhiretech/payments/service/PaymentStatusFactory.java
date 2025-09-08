package com.hulkhiretech.payments.service;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hulkhiretech.payments.enums.TransactionStatusEnum;
import com.hulkhiretech.payments.service.interfaces.TxnStatusProcessor;
import com.hulkhiretech.payments.service.processors.CreatedStatusProcessor;
import com.hulkhiretech.payments.service.processors.InitiatedStatusProcessor;
import com.hulkhiretech.payments.service.processors.PendingStatusProcessor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentStatusFactory
{
	private final ApplicationContext context;
	
	public TxnStatusProcessor getProcessor(TransactionStatusEnum txnStatus)
	{
		log.info("Fetching processor for txnStatusId: " + txnStatus.name());
		
		switch(txnStatus)
		{
			case CREATED:   return context.getBean(CreatedStatusProcessor.class);
			case INITIATED: return context.getBean(InitiatedStatusProcessor.class);
			case PENDING:   return context.getBean(PendingStatusProcessor.class);
		}
		return null;
	}
}
