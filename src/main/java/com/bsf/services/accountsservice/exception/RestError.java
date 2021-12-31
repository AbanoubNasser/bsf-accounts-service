package com.bsf.services.accountsservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author AbanoubNasser
 */
public interface RestError {

    String error();

    HttpStatus httpStatus();

    String desceription();

}
