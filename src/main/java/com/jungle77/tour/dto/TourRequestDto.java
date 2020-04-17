package com.jungle77.tour.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourRequestDto {
	
	@NotNull
	private String startingCountryCode;
	
	@NotNull
	private Integer budgetPerCountry;
	
	@NotNull
	private Integer totalBudget;
	
	@NotNull
	private String currency;
	
}

