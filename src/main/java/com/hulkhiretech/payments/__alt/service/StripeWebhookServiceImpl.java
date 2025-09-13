package com.hulkhiretech.payments.__alt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.JsonSyntaxException;
import com.hulkhiretech.payments.__alt.ErrorCodeEnum;
import com.hulkhiretech.payments.__alt.exception.CustomProviderException;
import com.hulkhiretech.payments.__alt.service.interfaces.StripeWebhookServiceInterface;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class StripeWebhookServiceImpl implements StripeWebhookServiceInterface
{
	private final ProcessStripeEventAsync processStripeEventAsync;
	
	@Value("${stripe.webhook.secret.v1}")
	private String kSecret;
	
	
	@PostConstruct
	private void OnInit()
	{
		log.info("Initialized StripeWebhookServiceImpl with webhook signing secret: '{}'", kSecret);
	}
	
	@Override
	public void webhookCall(String sigHeader, String payload)
	{
		Event event = CheckSignature(payload, sigHeader, kSecret);

		// log.info("Received event of type '{}' with id '{}' is valid", event.getType(), event.getId());
		
		processStripeEventAsync.processEvent(event);
		/*
		final var dataObjectDeserializer = event.getDataObjectDeserializer();
		StripeObject stripeObject = null;
		if (dataObjectDeserializer.getObject().isPresent())
		{
			stripeObject = dataObjectDeserializer.getObject().get();
		}
		else
		{
			// Deserialization failed, probably due to an API version mismatch.
			// Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
			// instructions on how to handle this case, or return an error here.
		}
		
		switch (event.getType())
		{
			case "payment_intent.succeeded":
			{
				PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
				log.info("PaymentIntent was successful!");
				break;
			}
			case "payment_intent.payment_failed":
			{
				PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
				log.info("PaymentIntent failed!");
				break;
			}
			case "payment_method.attached":
			{
				PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
				log.info("PaymentMethod was attached to a Customer!");
				break;
			}
			case "payment_method.detached":
			{
				PaymentMethod paymentMethod = (PaymentMethod) stripeObject;
				log.info("PaymentMethod was detached from a Customer!");
				break;
			}
			case "checkout.session.completed":
			{
				Session session = (Session) stripeObject;
				log.info("Checkout session completed!");
				break;
			}
			case "checkout.session.async_payment_succeeded":
			{
				Session session = (Session) stripeObject;
				log.info("Checkout session async payment succeeded!");
				break;
			}
			default: // ... handle other event types
			{
				log.error("Unhandled event type: " + event.getType());
			}
		}
		*/
	}
	
	private static Event CheckSignature(String payload, String sigHeader, String secret)
	{
		Event event;
		
		try
        {
            // Verify the event by signature
            event = Webhook.constructEvent(payload, sigHeader, secret);
        }
		catch (JsonSyntaxException e)
		{
			// Invalid payload
			log.error("Error parsing payload: " + e.getMessage());
			throw new CustomProviderException(
				ErrorCodeEnum.GENERIC_ERROR.getErrorMessage(),
				HttpStatus.BAD_REQUEST
			);
		}
        catch (SignatureVerificationException e)
        {
            // Invalid signature
			log.error("Error verifying webhook signature: " + e.getMessage());
            throw new CustomProviderException(
				ErrorCodeEnum.INVALID_STRIPE_SIGNATURE.getErrorMessage(),
				HttpStatus.BAD_REQUEST
			);
        }
		
		return event;
	} 
}
