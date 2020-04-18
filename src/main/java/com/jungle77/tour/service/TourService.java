package com.jungle77.tour.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jungle77.tour.dto.CountryCurrency;
import com.jungle77.tour.dto.CountryDto;
import com.jungle77.tour.dto.CountryTripDto;
import com.jungle77.tour.dto.CurrencyRatesDto;
import com.jungle77.tour.dto.TourRequestDto;
import com.jungle77.tour.dto.TourResponseDto;

@Service
public class TourService {
	
	@Autowired
	private NeighborCountriesServiceClient neighborCountriesServiceClient;
	
	@Autowired
	private CurrencyServiceClient currencyServiceClient;
	
	public TourResponseDto evaluateTour(TourRequestDto tourRequestDto) {
		
		// get neighbor countries
		List<CountryDto> countries = neighborCountriesServiceClient.getNeighborCountries(tourRequestDto.getStartingCountryCode());
		if (countries.isEmpty()) return null; 
		
		// get currencies for neighbor countries
    	List<String> currencies = countries.stream().map(e -> getCurrencyFromCountryCode(e.getCountry_code())).collect(Collectors.toList());
    	CurrencyRatesDto currencyRates = currencyServiceClient.getCurrencyValues(tourRequestDto.getCurrency(), currencies);
    	if (currencyRates == null) return null;
    	
    	// prepare response 
    	TourResponseDto tourResponseDto= new TourResponseDto();
    	tourResponseDto.setNbCompleteRoundTrips(tourRequestDto.getTotalBudget() / (tourRequestDto.getBudgetPerCountry() * countries.size()));
    	tourResponseDto.setLeftover(tourRequestDto.getTotalBudget() - tourResponseDto.getNbCompleteRoundTrips() * countries.size() * tourRequestDto.getBudgetPerCountry());
    	List<CountryTripDto> trips = new ArrayList<>();
    	countries.forEach(countryDto -> {
    		String currency = CountryCurrency.valueOf(countryDto.getCountry_code()).getCurrency();
    		String countryName = countryDto.getCountry_name();
    		Double amount = currencyRates.getRates().get(currency).doubleValue() * tourRequestDto.getBudgetPerCountry();
    		CountryTripDto countryTripDto = new CountryTripDto();
    		countryTripDto.setCountryName(countryName);
    		countryTripDto.setCurrency(currency);
    		countryTripDto.setAmount(amount);
    		trips.add(countryTripDto);
    	});
    	tourResponseDto.setTrips(trips);
    	
    	return tourResponseDto;
	}
	
	private String getCurrencyFromCountryCode(String countryCode) {
		return CountryCurrency.valueOf(countryCode).getCurrency();
	}
	
}
