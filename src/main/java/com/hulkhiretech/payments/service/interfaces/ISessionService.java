package com.hulkhiretech.payments.service.interfaces;

import com.hulkhiretech.payments.controller.pojo.SessionCreateRequest;
import com.hulkhiretech.payments.controller.pojo.SessionResponse;
import com.stripe.exception.StripeException;

public interface ISessionService
{
    SessionResponse createSession(SessionCreateRequest request) throws StripeException;
    
	SessionResponse retrieveSession(String sessionId) throws StripeException;

	SessionResponse expireSession(String sessionId) throws StripeException;
}
