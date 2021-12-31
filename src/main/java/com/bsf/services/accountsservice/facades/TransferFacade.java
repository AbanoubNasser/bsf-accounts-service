package com.bsf.services.accountsservice.facades;

import com.bsf.services.accountsservice.api.bo.TransferRequest;
import com.bsf.services.accountsservice.exception.ServiceError;
import com.bsf.services.accountsservice.model.Account;
import com.bsf.services.accountsservice.model.Transaction;
import com.bsf.services.accountsservice.repositories.AccountRepository;
import com.bsf.services.accountsservice.repositories.TransactionRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * TransferFacade is responsible for transfers validation and processing
 */
@Component
@Transactional
public class TransferFacade {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public TransferFacade(final AccountRepository accountRepository, final TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * processTransfer is responsible for loading accounts of transfer
     * validate the balance of from Account and process
     * by deducting the amount from one account and add it to other one.
     *
     * @param transferRequest
     */
    public void processTransfer(final TransferRequest transferRequest) {
        final Account fromAccount = retrieveAccount(transferRequest.getFromAccount());
        final Account toAccount = retrieveAccount(transferRequest.getToAccount());
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (Double.compare(transferRequest.getAmount(), fromAccount.getBalance()) < 0) {
                    accountRepository.updateAccountBalance(transferRequest.getAmount() * -1, fromAccount.getNumber());
                    accountRepository.updateAccountBalance(transferRequest.getAmount(), toAccount.getNumber());
                    generateTransferTransactions(fromAccount, toAccount, transferRequest.getAmount());
                } else {
                    throw ServiceError.NO_ENOUGH_BALANCE_ERROR.buildExcpetion();
                }
            }
        }
    }

    private void generateTransferTransactions(final Account fromAccount, final Account toAccount, final double amount) {

        Transaction fromAccountTX = new Transaction();
        fromAccountTX.setAccount(fromAccount);
        fromAccountTX.setAmount(amount * -1);

        transactionRepository.save(fromAccountTX);

        Transaction toAccountTX = new Transaction();
        toAccountTX.setAccount(toAccount);
        toAccountTX.setAmount(amount);

        transactionRepository.save(toAccountTX);
    }

    /**
     * retrieveAccount is responsible for loading account by account number from DB
     *
     * @param accountNumber
     * @return
     */
    private Account retrieveAccount(String accountNumber) {
        return accountRepository.findByNumber(accountNumber).orElseThrow(() -> ServiceError.ACCOUNT_NOT_FOUND_ERROR.buildExcpetion());
    }

}
