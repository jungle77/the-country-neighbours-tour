package com.jungle77.tour.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jungle77.tour.dto.CurrencyRatesDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CurrencyServiceClient {

	private final String WEB_SERVICE_URL_PREFIX = "http://data.fixer.io/api/latest?access_key=1b8dd2980fee5c2fa1ecfe19f77b561e&format=1&base=";
	private final String WEB_SERVICE_URL_SUFFIX = "&symbols=";
	
	private RestTemplate restTemplate;
	
	public CurrencyServiceClient() {
		restTemplate = new RestTemplate();
	}
	
	public CurrencyRatesDto getCurrencyValues(String baseCurrency, List<String> targetCurrencies) {
		
		final StringBuffer requestUrlBuffer = new StringBuffer(WEB_SERVICE_URL_PREFIX + baseCurrency + WEB_SERVICE_URL_SUFFIX);
		targetCurrencies.forEach(currency -> requestUrlBuffer.append(currency + ","));
		String requestUrl = requestUrlBuffer.toString().substring(0, requestUrlBuffer.length()-1);
		try {
			CurrencyRatesDto currencyRatesDto = restTemplate.getForObject(requestUrl, CurrencyRatesDto.class);
			return currencyRatesDto;
		} catch(RestClientException e) {
			log.error("Error getting currencies.", e);
			return null;
		}
	}

	
}
