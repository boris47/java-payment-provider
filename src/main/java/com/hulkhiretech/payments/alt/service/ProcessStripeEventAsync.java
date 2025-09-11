package com.hulkhiretech.payments.alt.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.stripe.model.Event;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProcessStripeEventAsync
{
	// @Async only works on public methods called from outside the bean (through Spring proxy).
	// If you call an @Async method from inside the same class, it won’t run async.
	// Exceptions thrown inside async methods don’t propagate to the caller (they’re logged instead).
	// Always define a proper thread pool in production (otherwise you’ll get too many threads).
	@Async
	public CompletableFuture<Void> processEvent(Event event)
	{
		// Process the event asynchronously
		
		log.info("Processing event of type '{}' with id '{}'", event.getType(), event.getId());
		
		return CompletableFuture.completedFuture(null);
	}
}
