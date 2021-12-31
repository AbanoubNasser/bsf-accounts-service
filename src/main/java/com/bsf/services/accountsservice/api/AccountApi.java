package com.bsf.services.accountsservice.api;

import com.bsf.services.accountsservice.api.bo.AccountData;
import com.bsf.services.accountsservice.facades.AccountFacade;
import com.bsf.services.accountsservice.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * AccountApi is a rest controller for account management
 *
 * @author AbanoubNasser
 */
@RestController
@RequestMapping("/api/accounts")
@Api(value = "Accounts EndPoints", description = "Accounts Endpoints")
@Slf4j
public class AccountApi {

    private final AccountFacade accountFacade;

    public AccountApi(final AccountFacade accountFacade) {
        this.accountFacade = accountFacade;
    }

    /**
     * retrieve Account by account Number.
     *
     * @return the response entity
     */
    @RequestMapping(value = "/{accountNumber}", method = RequestMethod.GET)
    @ApiOperation(value = "fetch account by account number", response = ResponseEntity.class)
    public ResponseEntity<?> getAgencyWithCode(@NotNull @PathVariable final String accountNumber) {
        log.info("AccountApi has been called to fetch account  with specific number");
        AccountData fetchedAccount = accountFacade.retrieveAccountByNumber(accountNumber);
        return ResponseUtil.wrapOrNotFound(fetchedAccount);
    }
}
