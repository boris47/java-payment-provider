package com.hulkhiretech.payments.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.dao.interfaces.TransactionDao;
import com.hulkhiretech.payments.entity.TransactionDTO;
import com.hulkhiretech.payments.enums.TransactionStatusEnum;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.TxnResponse;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;
import com.hulkhiretech.payments.service.helpers.CreatePaymentHelper_Stripe;
import com.hulkhiretech.payments.service.interfaces.PaymentServiceInterface;
import com.hulkhiretech.payments.service.interfaces.PaymentStatusServiceInterface;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentServiceInterface
{
	private final PaymentStatusServiceInterface paymentStatusService;
	private final TransactionDao transactionDao;
	private final ModelMapper modelMapper;
	private final HttpServiceEngine httpServiceEngine;
	private final CreatePaymentHelper_Stripe createPaymentHelper_Stripe;
	
	/** Create txn in DB */
	@Override
	public TxnResponse createTxn(CreateTxnRequest request)
	{
		log.info("Creating transaction in service layer");
		
		// Generate unique reference
		final var uniqueGeneratedReference = UUID.randomUUID().toString();
		
		// Map request to entity
		final var transactionDTO = modelMapper.map(request, TransactionDTO.class);
		{
			transactionDTO.setTxnReference(uniqueGeneratedReference);
			
			// Set initial status as Created
			transactionDTO.setTxnStatus(TransactionStatusEnum.CREATED.name());
		}
		
		// Process the status change
		final var dto = paymentStatusService.processStatus(transactionDTO);
		
		// Create response
		final var response = new TxnResponse();
		{
			response.setTxnReference(dto.getTxnReference());
			response.setTxnStatus(dto.getTxnStatus());
		}
		return response;
	}
	
	/**
	 * - Initiate txn in DB
	 * - Make Rest APU calls to stripe-provider-service for create-payment api
	 * - Update DB as Pending
	 * - Return url back to invoker
	 */
	@Override
	public TxnResponse initiateTxn(String txnReference, InitiateTxnRequest request)
	{
		log.info("Initiating transaction with id: {} in service layer, req {}", txnReference, request);
		
		TransactionDTO transactionDTO;
		
		// Fetch txn from DB using reference
		{
			// Use reference to fetch the entity from DB
			final var entity = transactionDao.getTransactionByReference(txnReference);
			
			// Map entity to DTO
			transactionDTO = modelMapper.map(entity, TransactionDTO.class);
		}
		
		// Set transaction as Initiated in DB
		{
			transactionDTO.setTxnStatus(TransactionStatusEnum.INITIATED.name());
			
			log.info("Update to Initiated: {}", transactionDTO);
			
			// Process the status change
			transactionDTO = paymentStatusService.processStatus(transactionDTO);
		}
		
		// Call stripe-provider-service to create payment
		HttpRequest req = createPaymentHelper_Stripe.PrepareHttpReq(request);
		var res = httpServiceEngine.MakeRequest(req);
		var ress = createPaymentHelper_Stripe.ProcessResponse(res);
		
		// Set transaction as Pending in DB
		{
			transactionDTO.setTxnStatus(TransactionStatusEnum.PENDING.name());
			transactionDTO.setProviderReference(ress.getId());
			
			log.info("Update to Pending: {}", transactionDTO);
			
			// Process the status change
			transactionDTO = paymentStatusService.processStatus(transactionDTO);
		}
		
		// Create response
		final var response = new TxnResponse();
		{
			response.setTxnReference(transactionDTO.getTxnReference());
			response.setTxnStatus(transactionDTO.getTxnStatus());
			response.setRedirectUrl(ress.getUrl());
		}
		
		return response;
	}
}
