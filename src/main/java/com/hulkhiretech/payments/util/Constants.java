package com.hulkhiretech.payments.util;

public class Constants
{
	/**
	 * The tolerance time, in seconds, to use when verifying the signature.
	 * It’s the maximum age (in seconds) a webhook timestamp is allowed to be.
	 * Default best practice from Stripe: 300 seconds (5 minutes).
	 * If the webhook’s timestamp is older than now - tolerance,
	 * verification fails with SignatureVerificationException
	 * 
	 * https://stripe.com/docs/webhooks/signatures#verify-manually
	 * 
	 * This protects you from replay attacks (someone copying a valid webhook and resending it hours later).
	 * It also allows a little slack for network delays.
	 */
	public static final long TOLERANCE_SECONDS = 300L;
}
