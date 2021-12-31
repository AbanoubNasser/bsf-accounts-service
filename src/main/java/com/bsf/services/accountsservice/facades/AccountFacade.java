package com.bsf.services.accountsservice.facades;

import com.bsf.services.accountsservice.api.bo.AccountData;
import com.bsf.services.accountsservice.exception.ServiceError;
import com.bsf.services.accountsservice.model.Account;
import com.bsf.services.accountsservice.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * AccountFacade is responsible for Account management
 */
@Component
public class AccountFacade {

    private final AccountRepository accountRepository;

    public AccountFacade(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * retrieveAccountByNumber is responsible for retrieving account by account number
     *
     * @param accountNumber
     * @return AccountData
     */
    public AccountData retrieveAccountByNumber(final String accountNumber) {
        Optional<Account> accountOptional = accountRepository.findByNumber(accountNumber);
        if (accountOptional.isPresent()) {

            Account account = accountOptional.get();
            return AccountData.builder()
                    .number(account.getNumber())
                    .ownerName(account.getOwnerName())
                    .bankName(account.getBankName())
                    .balance(account.getBalance()).build();
        }
        throw ServiceError.ACCOUNT_NOT_FOUND_ERROR.buildExcpetion();
    }
}
