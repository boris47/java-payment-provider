package com.hulkhiretech.payments;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAsync
@SpringBootApplication
public class StripeProviderServiceApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(StripeProviderServiceApplication.class, args);
	}
}
