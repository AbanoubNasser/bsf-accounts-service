package com.bsf.services.accountsservice.exception;

import lombok.Getter;

/**
 * @author AbnaoubNasser
 */
@Getter
public class ServiceException extends RestException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -3102904573968573206L;

    /**
     * The Service errors.
     */
    private ServiceError serviceError;

    /**
     * Instantiates a new Service exception.
     *
     * @param serviceError the service error
     */
    public ServiceException(final ServiceError serviceError) {
        super(serviceError);
        this.serviceError = serviceError;
    }

}
