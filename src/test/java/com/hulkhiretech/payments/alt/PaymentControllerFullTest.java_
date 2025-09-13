package com.hulkhiretech.payments.alt;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.hulkhiretech.payments.alt.controller.pojo.ResponseCreatePayment;
import com.hulkhiretech.payments.alt.controller.pojo.ResponseExpirePayment;
import com.hulkhiretech.payments.alt.controller.pojo.ResponseRetrievePayment;

@SpringBootTest(
	properties = "spring.profiles.active=test",
	webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class PaymentControllerFullTest
{
	@Autowired
	private TestRestTemplate restTemplate;
	
	private final int serverPort = 8083;
	
	// Add test methods here
	@Test
	void testPaymentFlow()
	{
		String resourceId = null;
		
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
			
			final String baseUrl = "http://localhost:" + serverPort + "/v1/payments";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<>(createPaymentJSON, headers);
			
			// Create the payment
			ResponseCreatePayment response = restTemplate.postForObject(baseUrl, request, ResponseCreatePayment.class);
			
			Assertions.assertNotNull(response);
			Assertions.assertInstanceOf(ResponseCreatePayment.class, response);
			
			Assertions.assertNotNull(response.getId());
			Assertions.assertFalse(response.getId().isBlank());
			Assertions.assertNotNull(response.getUrl());
			Assertions.assertFalse(response.getUrl().isBlank());
			
			resourceId = response.getId();
		}
		
		{
			if (resourceId == null || resourceId.isBlank())
			{
				Assertions.fail("Resource ID is null or blank. Create payment might have failed.");
				return;
			}
			
			final String baseUrl = "http://localhost:" + serverPort + "/v1/payments/" + resourceId;
			
			ResponseRetrievePayment response = restTemplate.getForObject(baseUrl, ResponseRetrievePayment.class);
			
			Assertions.assertNotNull(response);
			Assertions.assertInstanceOf(ResponseRetrievePayment.class, response);
			Assertions.assertNotNull(response.getId());
			Assertions.assertEquals(resourceId, response.getId());
			Assertions.assertNotNull(response.getStatus());
			Assertions.assertEquals("open", response.getStatus());
			Assertions.assertNotNull(response.getPaymentStatus());
			Assertions.assertEquals("unpaid", response.getPaymentStatus());
		}
		
		{
			if (resourceId == null || resourceId.isBlank())
			{
				Assertions.fail("Resource ID is null or blank. Create payment might have failed.");
				return;
			}
			
			final String baseUrl = "http://localhost:" + serverPort + "/v1/payments/" + resourceId + "/expire";
			
			ResponseExpirePayment response = restTemplate.postForObject(baseUrl, null, ResponseExpirePayment.class);
			
			Assertions.assertNotNull(response);
			Assertions.assertInstanceOf(ResponseExpirePayment.class, response);
			Assertions.assertNotNull(response.getId());
			Assertions.assertEquals(resourceId, response.getId());
			Assertions.assertNotNull(response.getPaymentStatus());
			Assertions.assertEquals("unpaid", response.getPaymentStatus());
			Assertions.assertNotNull(response.getStatus());
			Assertions.assertEquals("expired", response.getStatus());
		}
	}
}
