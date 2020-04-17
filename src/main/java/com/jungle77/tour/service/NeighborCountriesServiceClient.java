package com.jungle77.tour.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.jungle77.tour.dto.CountryDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NeighborCountriesServiceClient {
	
	private final String WEB_SERVICE_URL_PREFIX = "https://api.geodatasource.com/neighboring-countries?key=MDT8IG5TCVV0EH4B15E8CMJM5FZ0MOKM&format=json&country_code=";
	
	private RestTemplate restTemplate;
	
	public NeighborCountriesServiceClient() {
		restTemplate = new RestTemplate();
	}
	
	public List<CountryDto> getNeighborCountries(String countryCode) {
		
		try {
			CountryDto[] response = restTemplate.getForObject(WEB_SERVICE_URL_PREFIX + countryCode, CountryDto[].class);
			return Arrays.asList(response);
		} catch(RestClientException e) {
			log.error("Error getting neighbor countries.", e);
			return Collections.emptyList();
		}
	}
	
}
