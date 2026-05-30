package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.IncentiveRecord;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IncentiveApiClient {
	private final RestTemplate restTemplate;
	private final String incentiveUrl;

	public IncentiveApiClient(RestTemplate restTemplate, @Value("${general.incentive-api-url}") String incentiveUrl){
		this.restTemplate = restTemplate;
		this.incentiveUrl = incentiveUrl;
	}

	public float getIncentive(Transaction transaction){

		IncentiveRecord response = restTemplate.postForObject(incentiveUrl+"/incentive", transaction, IncentiveRecord.class);
		return response == null ? 0f : response.getAmount();
	}

}
