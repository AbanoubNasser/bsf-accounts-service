package com.bsf.services.accountsservice.exception;

import lombok.Getter;

/**
 * @author AbanoubNasser
 */
@Getter
public class RestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Service errors.
     */
    private RestError restError;

    /**
     * Instantiates a new Service exception.
     *
     * @param restError
     */
    public RestException(final RestError restError) {
        super(restError.desceription());
        this.restError = restError;
    }

}
