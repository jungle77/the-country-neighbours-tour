package com.jungle77.tour.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TourBadRequestException extends TourException {
    public TourBadRequestException(String msg) {
    	super(msg);
    }
}