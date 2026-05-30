package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class TransactionRecord {

	@Id
	@GeneratedValue()
	private long id;

	@ManyToOne
	@JoinColumn(name = "sender_id", nullable = false)
	private UserRecord sender;

	@ManyToOne
	@JoinColumn(name = "recipient_id", nullable = false)
	private UserRecord recipient;

	@Column(nullable = false)
	private float amount;

	@Column(nullable = false)
	private float incentive;

	@Column(nullable = false)
	private boolean isApplied;

	protected TransactionRecord() {
	}

	public TransactionRecord(UserRecord sender, UserRecord recipient, float amount, float incentive, boolean isApplied) {
		this.sender = sender;
		this.recipient = recipient;
		this.amount = amount;
		this.incentive = incentive;
		this.isApplied = isApplied;
	}

	public long getId() {
		return id;
	}

	public UserRecord getSender() {
		return sender;
	}

	public UserRecord getRecipient() {
		return recipient;
	}

	public float getAmount() {
		return amount;
	}

	public float getIncentive(){return incentive;}

	public boolean isApplied() {
		return isApplied;
	}
}
