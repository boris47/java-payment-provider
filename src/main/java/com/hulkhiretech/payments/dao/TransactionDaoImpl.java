package com.hulkhiretech.payments.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
		final var keyHolder = new GeneratedKeyHolder();
		final var paramSource = new BeanPropertySqlParameterSource(entity);
		
		jdbcTemplate.update(INSERT_SQL, paramSource, keyHolder, new String[] { "id" });
		
		// Set the generated id back to the entity
		entity.setId(keyHolder.getKey().intValue());
		log.info("Inserted entity: {}", entity);
		return entity.getId();
	}
	
	
	private static final String RETRIEVE_SQL = """
		SELECT * FROM payments.`Transaction`
		WHERE txnReference = :txnReference
	""";
	
	@Override
	public TransactionEntity getTransactionByReference(String txnReference)
	{
		final var param = new MapSqlParameterSource();
		{
			param.addValue("txnReference", txnReference);
		}
		return jdbcTemplate.queryForObject(RETRIEVE_SQL, param, new BeanPropertyRowMapper<>(TransactionEntity.class));
	}
	
	private static final String UPDATE_STATUS_SQL = """
		UPDATE payments.`Transaction`
		SET txnStatusId = :txnStatusId,
		    providerReference = :providerReference
		WHERE txnReference = :txnReference
	""";
	
	public Integer UpdateTransactionDetailsByReference(TransactionEntity entity)
	{
		final var param = new MapSqlParameterSource();
		{
			// Identify the transaction to be updated
			param.addValue("txnReference", entity.getTxnReference());
			// Identify the transaction inside defined provider
			param.addValue("providerReference", entity.getProviderReference());
			// The status of the transaction
			param.addValue("txnStatusId", entity.getTxnStatusId());
		}
		return jdbcTemplate.update(UPDATE_STATUS_SQL, param);
	}
}
