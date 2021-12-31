package com.bsf.services.accountsservice.api;

import com.bsf.services.accountsservice.api.bo.TransferRequest;
import com.bsf.services.accountsservice.facades.TransferFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * TransferApi is a rest controller for transfer management
 *
 * @author AbanoubNasser
 */
@RestController
@RequestMapping("/api/transfers")
@Api(value = "Transfers EndPoints", description = "Transfers Endpoints")
public class TransferApi {

    private final TransferFacade transferFacade;

    public TransferApi(final TransferFacade transferFacade) {
        this.transferFacade = transferFacade;
    }

    /**
     * processTransfer is an api responsible from processing transfer request
     *
     * @param transferRequest
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(value = "process transfer", response = ResponseEntity.class)
    public ResponseEntity<?> processTransfer(@RequestBody @Valid final TransferRequest transferRequest) {
        transferFacade.processTransfer(transferRequest);
        return ResponseEntity.ok().build();
    }
}
