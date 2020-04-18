package com.jungle77.tour.resource;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jungle77.tour.dto.CountryCurrency;
import com.jungle77.tour.dto.TourRequestDto;
import com.jungle77.tour.dto.TourResponseDto;
import com.jungle77.tour.exceptions.TourBadRequestException;
import com.jungle77.tour.exceptions.TourException;
import com.jungle77.tour.exceptions.TourInternalErrorException;
import com.jungle77.tour.service.TourService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/v1")
@Slf4j
public class TourResource {
	
	@Autowired
	private TourService tourService;
	
    @ResponseBody
    @PostMapping(value = "/tour", consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public TourResponseDto evaluateTour(@Valid @RequestBody TourRequestDto tourRequestDto) throws TourException {
    	
    	log.info("Received request for tour evaluation: {}", tourRequestDto);
    	
    	validateRequest(tourRequestDto);
    	
    	TourResponseDto tourResponseDto = tourService.evaluateTour(tourRequestDto);
    	
    	if (tourResponseDto == null) {
    		throw new TourInternalErrorException();
    	}
    	
    	log.info("Tour evaluated: {}", tourResponseDto);
    	
    	return tourResponseDto;
    }
    
    private void validateRequest(TourRequestDto tourRequestDto) throws TourException {
    	if (CountryCurrency.fromCurrency(tourRequestDto.getCurrency().toUpperCase()) == null) {
    		throw new TourBadRequestException(tourRequestDto.getCurrency() + " is not a valid currency code");
    	}
    	
    	try {
    		CountryCurrency.valueOf(tourRequestDto.getStartingCountryCode().toUpperCase());
    	} catch (IllegalArgumentException e) {
    		throw new TourBadRequestException(tourRequestDto.getStartingCountryCode() + " is not a valid country code");
    	}
    	tourRequestDto.setStartingCountryCode(tourRequestDto.getStartingCountryCode().toUpperCase());
    	tourRequestDto.setCurrency(tourRequestDto.getCurrency().toUpperCase());
    }
    
}
