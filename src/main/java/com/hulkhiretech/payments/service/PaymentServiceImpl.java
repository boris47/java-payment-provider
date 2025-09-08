package com.hulkhiretech.payments.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.entity.TransactionDTO;
import com.hulkhiretech.payments.enums.TransactionStatusEnum;
import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.CreateTxnResponse;
import com.hulkhiretech.payments.pojo.InitiateTxnRequest;
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
	
	private final ModelMapper modelMapper;
	
	/** Create txn in DB */
	@Override
	public CreateTxnResponse createTxn(CreateTxnRequest request)
	{
		log.info("Creating transaction in service layer");
		
		final var transactionDTO = modelMapper.map(request, TransactionDTO.class);
		final String uniqueGeneratedReference = UUID.randomUUID().toString();
		{
			transactionDTO.setTxnStatus(TransactionStatusEnum.CREATED.name());
			transactionDTO.setTxnReference(uniqueGeneratedReference);
			log.info("Mapped entity: {}", transactionDTO);
		}
		final TransactionDTO dto = paymentStatusService.processStatus(transactionDTO);
		final CreateTxnResponse response = new CreateTxnResponse();
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
	public String initiateTxn(String id, InitiateTxnRequest request)
	{
		log.info("Initiating transaction with id: {} in service layer", id);
		
	//	var response = paymentStatusService.processStatus(1);
		
		return "";
	}
}
