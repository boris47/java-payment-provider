package com.hulkhiretech.payments.dao;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.hulkhiretech.payments.dao.interfaces.TransactionDao;
import com.hulkhiretech.payments.entity.TransactionEntity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TransactionDaoImpl implements TransactionDao
{
	private final NamedParameterJdbcTemplate jdbcTemplate;
	
	private static final String INSERT_SQL = """
		INSERT INTO payments.`Transaction` (
			userId, paymentMethodId, providerId, paymentTypeId, txnStatusId,
			amount, currency, merchantTransactionReference, txnReference
		) VALUES (
			:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId,
			:amount, :currency, :merchantTransactionReference, :txnReference
		)
	""";
	
	@Override
	public Integer insertTransaction(TransactionEntity entity)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource paramSource = new BeanPropertySqlParameterSource(entity);
		
		jdbcTemplate.update(INSERT_SQL, paramSource, keyHolder, new String[] { "id" });
		
		// Set the generated id back to the entity
		entity.setId(keyHolder.getKey().intValue());
		log.info("Inserted entity: {}", entity);
		return entity.getId();
	}

}
