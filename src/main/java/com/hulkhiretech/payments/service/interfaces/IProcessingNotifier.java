package com.hulkhiretech.payments.service.interfaces;

public interface IProcessingNotifier
{
	void notifySuccess(String txtReference);
	void notifyFailure(String txtReference);
}
