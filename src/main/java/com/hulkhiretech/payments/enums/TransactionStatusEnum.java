package com.hulkhiretech.payments.enums;

public enum TransactionStatusEnum
{
	/**
	 * Insert a new empty transaction into DB, waiting for initiation
	 */
	CREATED(1, "CREATED"),
	/**
	 * Transaction has been initiated with payment provider, waiting for user action
	 */
	INITIATED(2, "INITIATED"),
	/**
	 * Transaction is being processed by payment provider, waiting for final status
	 */
	PENDING(3, "PENDING"),
	/**
	 * Transaction has been completed successfully
	 */
	SUCCESS(4, "SUCCESS"),
	/**
	 * Transaction has failed
	 */
	FAILED(5, "FAILED");

	private final int id;
	private final String name;

	TransactionStatusEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() { return id; }
	public String getName() { return name; }

	public static TransactionStatusEnum fromId(int id) {
		for (TransactionStatusEnum e : values()) {
			if (e.id == id) return e;
		}
		return null;
	}

	public static TransactionStatusEnum fromName(String name) {
		for (TransactionStatusEnum e : values()) {
			if (e.name.equalsIgnoreCase(name)) return e;
		}
		return null;
	}
}
