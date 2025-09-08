package com.hulkhiretech.payments.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hulkhiretech.payments.entity.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;
import com.hulkhiretech.payments.pojo.CreateTxnRequest;
import com.hulkhiretech.payments.pojo.CreateTxnResponse;
import com.hulkhiretech.payments.util.PaymentMethodEnumConverter;
import com.hulkhiretech.payments.util.PaymentTypeEnumConverter;
import com.hulkhiretech.payments.util.ProviderEnumConverter;
import com.hulkhiretech.payments.util.TransactionStatusEnumConverter;

@Configuration
public class AppConfig
{
	@Bean
	ModelMapper modelMapper()
	{
		var mapper = new ModelMapper();
		// Create Converters instances
		var PaymentMethodEnumConverter = new PaymentMethodEnumConverter();
		var ProviderEnumConverter = new ProviderEnumConverter();
		var PaymentTypeEnumConverter = new PaymentTypeEnumConverter();
		var TransactionStatusEnumConverter = new TransactionStatusEnumConverter();
		// Add Converters to ModelMapper
		{
		//	mapper.addConverter(a);
		//	mapper.addConverter(b);
		//	mapper.addConverter(c);
		//	mapper.addConverter(d);
		}
		// Add Mappings
		{
	//		mapper.typeMap(CreateTxnRequest.class, TransactionDTO.class)
	//			.addMappings(m -> {
	//				m.map(CreateTxnRequest::getPaymentMethod, TransactionDTO::setPaymentMethodId);
	//				m.map(CreateTxnRequest::getProvider, TransactionDTO::setProviderId);
	//				m.map(CreateTxnRequest::getPaymentType, TransactionDTO::setPaymentTypeId);
	//			})
	//		;
	//		mapper.typeMap(TransactionDTO.class, TransactionEntity.class)
	//			.addMappings(m -> {
	//				m.map(TransactionDTO::getTxnStatus, TransactionEntity::setTxnStatusId);
	//			})
	//		;
	//		mapper.typeMap(TransactionEntity.class, TransactionDTO.class)
	//			.addMappings(m -> {
	//				m.map(TransactionEntity::getTxnStatusId, TransactionDTO::setTxnStatus);
	//			})
	//		;
	//		mapper.typeMap(TransactionDTO.class, CreateTxnResponse.class)
	//			.addMappings(m -> {
	//				m.map(TransactionDTO::getTxnReference, CreateTxnResponse::setTxnReference);
	//				m.map(TransactionDTO::getTxnStatus, CreateTxnResponse::setTxnStatus);
	//			})
	//		;
		}
		
		{
			mapper.addMappings(new PropertyMap<TransactionDTO, TransactionEntity>()
			{
				@Override
				protected void configure()
				{
					using(PaymentMethodEnumConverter).map(source.getPaymentMethod(), destination.getPaymentMethodId());
					using(ProviderEnumConverter).map(source.getProvider(), destination.getProviderId());
					using(PaymentTypeEnumConverter).map(source.getPaymentType(), destination.getPaymentTypeId());
					using(TransactionStatusEnumConverter).map(source.getTxnStatus(), destination.getTxnStatusId());
				}
			});
		}
		{
			mapper.getConfiguration()
				// nly map properties that match by name and type
				.setMatchingStrategy(org.modelmapper.convention.MatchingStrategies.STRICT)
				// skip null values during mapping
				.setSkipNullEnabled(true)
			;
		}
		return mapper;
	}
}
