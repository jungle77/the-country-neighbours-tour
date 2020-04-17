package com.jungle77.tour.dto;

import java.util.List;

import lombok.Data;

@Data
public class TourResponseDto {
	
	private List<CountryTripDto> trips;
	private int nbCompleteRoundTrips;
	private int leftover;
	
}
