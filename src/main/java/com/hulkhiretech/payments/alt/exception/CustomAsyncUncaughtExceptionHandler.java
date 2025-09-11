package com.hulkhiretech.payments.alt.exception;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class CustomAsyncUncaughtExceptionHandler implements AsyncConfigurer
{
	@Override
	public Executor getAsyncExecutor()
	{
		// same as before — define a proper executor
		return java.util.concurrent.Executors.newFixedThreadPool(5);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler()
	{
		return new AsyncUncaughtExceptionHandler()
		{
			@Override
			public void handleUncaughtException(@NonNull Throwable ex, @NonNull Method method, @NonNull Object... params)
			{
				System.err.println("Async error in method: " + method.getName());
				ex.printStackTrace();
			}
		};
	}
}
