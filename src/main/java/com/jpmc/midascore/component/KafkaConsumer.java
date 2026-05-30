package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
	private final DatabaseConduit databaseConduit;
	private final UserRepository userRepository;
	private final IncentiveApiClient incentiveApiClient;

	public KafkaConsumer(DatabaseConduit databaseConduit, UserRepository userRepository, IncentiveApiClient incentiveApiClient) {
		this.databaseConduit = databaseConduit;
		this.userRepository = userRepository;
		this.incentiveApiClient = incentiveApiClient;
	}

	@KafkaListener(id = "midasListener", topics = "${general.kafka-topic}")
	public void listen(Transaction transaction) {
		boolean isApplied;
		if (userRepository.findById(transaction.getSenderId()).getBalance() >= transaction.getAmount()) {
			isApplied = true;
		} else {
			isApplied = false;
		}

		transaction.setIncentive(incentiveApiClient.getIncentive(transaction));

		TransactionRecord transactionRecord = new TransactionRecord(
			userRepository.findById(transaction.getSenderId()),
			userRepository.findById(transaction.getRecipientId()),
			transaction.getAmount(),
			transaction.getIncentive(),
			isApplied
		);

		if (isApplied) {
			UserRecord senderRecord = userRepository.findById(transaction.getSenderId());
			senderRecord.setBalance(senderRecord.getBalance() - transaction.getAmount());

			UserRecord recipientRecord = userRepository.findById(transaction.getRecipientId());
			recipientRecord.setBalance(recipientRecord.getBalance() + transaction.getAmount() + transaction.getIncentive());

			System.out.println("Sender: " + senderRecord);
			System.out.println("Recipient: " + recipientRecord);

			databaseConduit.save(senderRecord);
			databaseConduit.save(recipientRecord);
		}
		databaseConduit.save(transactionRecord);
	}

}
