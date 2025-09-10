package com.hulkhiretech.payments.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hulkhiretech.payments.entity.TransactionDTO;
import com.hulkhiretech.payments.entity.TransactionEntity;
import com.hulkhiretech.payments.util.converters.PaymentMethodEnumIntToNameConverter;
import com.hulkhiretech.payments.util.converters.PaymentMethodEnumNameToIntConverter;
import com.hulkhiretech.payments.util.converters.PaymentTypeEnumIntToNameConverter;
import com.hulkhiretech.payments.util.converters.PaymentTypeEnumNameToIntConverter;
import com.hulkhiretech.payments.util.converters.ProviderEnumIntToNameConverter;
import com.hulkhiretech.payments.util.converters.ProviderEnumNameToIntConverter;
import com.hulkhiretech.payments.util.converters.TransactionStatusEnumIntToNameConverter;
import com.hulkhiretech.payments.util.converters.TransactionStatusEnumNameToIntConverter;

@Configuration
public class AppConfig
{
	@Bean
	ModelMapper modelMapper()
	{
		var mapper = new ModelMapper();
		
		{
			var PaymentMethodEnumNameToIntConverter = new PaymentMethodEnumNameToIntConverter();
			var ProviderEnumNameToIntConverter = new ProviderEnumNameToIntConverter();
			var PaymentTypeEnumNameToIntConverter = new PaymentTypeEnumNameToIntConverter();
			var TransactionStatusEnumNameToIntConverter = new TransactionStatusEnumNameToIntConverter();
			mapper.addMappings(new PropertyMap<TransactionDTO, TransactionEntity>()
			{
				@Override
				protected void configure()
				{
					using(PaymentMethodEnumNameToIntConverter).map(source.getPaymentMethod(), destination.getPaymentMethodId());
					using(ProviderEnumNameToIntConverter).map(source.getProvider(), destination.getProviderId());
					using(PaymentTypeEnumNameToIntConverter).map(source.getPaymentType(), destination.getPaymentTypeId());
					using(TransactionStatusEnumNameToIntConverter).map(source.getTxnStatus(), destination.getTxnStatusId());
				}
			});
		}
		{
			var PaymentMethodEnumConverter = new PaymentMethodEnumIntToNameConverter();
			var ProviderEnumConverter = new ProviderEnumIntToNameConverter();
			var PaymentTypeEnumConverter = new PaymentTypeEnumIntToNameConverter();
			var TransactionStatusEnumConverter = new TransactionStatusEnumIntToNameConverter();
			mapper.addMappings(new PropertyMap<TransactionEntity, TransactionDTO>()
			{
				@Override
				protected void configure()
				{
					using(PaymentMethodEnumConverter).map(source.getPaymentMethodId(), destination.getPaymentMethod());
					using(ProviderEnumConverter).map(source.getProviderId(), destination.getProvider());
					using(PaymentTypeEnumConverter).map(source.getPaymentTypeId(), destination.getPaymentType());
					using(TransactionStatusEnumConverter).map(source.getTxnStatusId(), destination.getTxnStatus());
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
