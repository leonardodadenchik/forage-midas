package com.jpmc.midascore.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserRecord {

	@Id
	@GeneratedValue()
	private long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private float balance;

	@OneToMany(mappedBy = "sender")
	private List<TransactionRecord> sentTransactions = new ArrayList<>();

	@OneToMany(mappedBy = "recipient")
	private List<TransactionRecord> receivedTransactions = new ArrayList<>();


	protected UserRecord() {
	}

	public UserRecord(String name, float balance) {
		this.name = name;
		this.balance = balance;
	}

	@Override
	public String toString() {
		return String.format("User[id=%d, name='%s', balance=%.2f]", id, name, balance);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public List<TransactionRecord> getSentTransactions(){return sentTransactions;}

	public List<TransactionRecord> getReceivedTransactions(){return receivedTransactions;}
}
