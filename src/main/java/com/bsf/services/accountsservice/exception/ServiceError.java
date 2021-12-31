package com.bsf.services.accountsservice.exception;

import org.springframework.http.HttpStatus;

/**
 * @author AbanoubNasser
 */
public enum ServiceError implements RestError {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ""),

    REQUEST_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, ""),

    ACCOUNT_NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "Account is not found"),

    NO_ENOUGH_BALANCE_ERROR(HttpStatus.BAD_REQUEST, "No Enough balance to transfer this amount");

    /**
     * The http status.
     */
    private HttpStatus httpStatus;
    /**
     * The description.
     */
    private String description;

    /**
     * Instantiates a new Service errors.
     *
     * @param httpStatus  the http status
     * @param description the description
     */
    private ServiceError(final HttpStatus httpStatus, final String description) {
        this.httpStatus = httpStatus;
        this.description = description;
    }

    @Override
    public String error() {
        return this.name();
    }

    @Override
    public HttpStatus httpStatus() {
        return this.httpStatus;
    }

    @Override
    public String desceription() {
        return this.description;
    }

    public RestException buildExcpetion() {
        return new ServiceException(this);
    }

}
