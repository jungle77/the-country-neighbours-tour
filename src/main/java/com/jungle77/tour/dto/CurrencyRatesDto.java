package com.jungle77.tour.dto;

import java.util.Map;

import lombok.Data;

@Data
public class CurrencyRatesDto {
	
	private Map<String,Double> rates;
	private String base;
	private String date;
	
}
