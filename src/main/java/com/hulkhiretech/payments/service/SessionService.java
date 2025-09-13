package com.hulkhiretech.payments.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.controller.pojo.SessionCreateRequest;
import com.hulkhiretech.payments.controller.pojo.SessionCreateRequest.Product;
import com.hulkhiretech.payments.controller.pojo.SessionResponse;
import com.hulkhiretech.payments.service.interfaces.ISessionService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public final class SessionService implements ISessionService
{
	private final static SessionCreateParams.PaymentMethodType kDefaultPaymentMethod
		= SessionCreateParams.PaymentMethodType.CARD;
	
	@Override
	public SessionResponse createSession(SessionCreateRequest request) throws StripeException
	{
		log.info("Creating session for request {}", request);
		
		SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
			.setMode(SessionCreateParams.Mode.PAYMENT)
			.setClientReferenceId(request.getClientReferenceId())
			.setSuccessUrl(request.getSuccessUrl())
			.setCancelUrl(request.getCancelUrl())
		;
		
		final var currency = request.getCurrency();
		ProcessProducts(paramsBuilder, request.getProducts(), currency);
		ProcessPaymentMethods(paramsBuilder, request.getPaymentMethods());
		
		Session session = Session.create(paramsBuilder.build());
		SessionResponse response = new SessionResponse(session);
		log.info("Created session: {}", response);
		return response;
	}
	
	@Override
	public SessionResponse retrieveSession(String sessionId) throws StripeException
	{
		Session session = Session.retrieve(sessionId);
		SessionResponse response = new SessionResponse(session);
		return response;
	}
	
	@Override
	public SessionResponse expireSession(String sessionId) throws StripeException
	{
		Session session = Session.retrieve(sessionId);
		Session expiredSession = session.expire();
		SessionResponse response = new SessionResponse(expiredSession);
		return response;
	}

	private final static void ProcessProducts(SessionCreateParams.Builder paramsBuilder, List<Product> products, String currency)
	{
		List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
		for (var product : products)
		{
			lineItems.add(
				SessionCreateParams.LineItem.builder()
					.setQuantity(product.getQuantity())
					.setPriceData(
						SessionCreateParams.LineItem.PriceData.builder()
							.setCurrency(currency)
							.setUnitAmount(product.getUnitAmount()) // price in cents
							.setProductData(
								SessionCreateParams.LineItem.PriceData.ProductData.builder()
									.setName(product.getProductName())
									.build()
							)
							.build()
					)
					.build()
			);
		}
		paramsBuilder.addAllLineItem(lineItems);
	}
	
	private final static void ProcessPaymentMethods(SessionCreateParams.Builder paramsBuilder, List<String> paymentMethods)
	{
		if (paymentMethods != null && !paymentMethods.isEmpty())
		{
			for (String pm : paymentMethods)
			{
				SessionCreateParams.PaymentMethodType paymentMethodType
					= SessionCreateParams.PaymentMethodType.valueOf(pm.toUpperCase());
				paramsBuilder.addPaymentMethodType(paymentMethodType);
			}
		}
		else
		{
			paramsBuilder.addPaymentMethodType(kDefaultPaymentMethod);
		}
	}
}
