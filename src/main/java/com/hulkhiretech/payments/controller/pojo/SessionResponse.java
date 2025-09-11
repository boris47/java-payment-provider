package com.hulkhiretech.payments.controller.pojo;

import com.stripe.model.checkout.Session;

import lombok.Data;

@Data
public class SessionResponse
{
	private String id;
	private String status;
	private String paymentStatus;
	private String paymentUrl;
	
	public SessionResponse(Session session)
	{
		this.id = session.getId();
		this.status = session.getStatus();
		this.paymentStatus = session.getPaymentStatus();
		this.paymentUrl = session.getUrl();
	}
}
