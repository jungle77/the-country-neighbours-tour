package com.jungle77.tour.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class TourInternalErrorException extends TourException {
    public TourInternalErrorException() {
    	super("Sorry, we wern't able to process your request. Try again later.");
    }
}