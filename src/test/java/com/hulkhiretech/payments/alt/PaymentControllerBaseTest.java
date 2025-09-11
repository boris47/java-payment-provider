package com.hulkhiretech.payments.alt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class PaymentControllerBaseTest
{
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void shouldReturnResponseCreateSession() throws Exception
	{
		final String createPaymentJSON = """
		{
			"successUrl": "https://example.com/success",
			"cancelUrl": "https://example.com/cancel",
			"lineItems": [
				{
				"currency": "EUR",
				"quantity": 1,
				"productName": "Sports wear",
				"unitAmount": 1000
				}
			]
		}
		""";
		
		mockMvc.perform(post("/v1/payments")
				.contentType("application/json")
				.content(createPaymentJSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").isString())
			.andExpect(jsonPath("$.url").isString());
	}
	
	@Test
	void shouldReturnNotFoundForInvalidRetrieveSession() throws Exception
	{
		mockMvc.perform(get("/v1/payments/invalid-id"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.errorMessages[0]").value("No such checkout.session: invalid-id"))
			;
	}
	
	@Test
	void shouldReturnNotFoundForInvalidExpireSession() throws Exception
	{
		mockMvc.perform(post("/v1/payments/invalid-id/expire"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.errorMessages[0]").value("No such checkout session: 'invalid-id'"))
			;
	}
}
