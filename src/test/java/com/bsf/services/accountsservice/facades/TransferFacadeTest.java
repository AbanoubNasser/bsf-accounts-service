package com.bsf.services.accountsservice.facades;

import com.bsf.services.accountsservice.api.bo.TransferRequest;
import com.bsf.services.accountsservice.exception.ServiceException;
import com.bsf.services.accountsservice.model.Account;
import com.bsf.services.accountsservice.repositories.AccountRepository;
import com.bsf.services.accountsservice.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class TransferFacadeTest {

    private TransferFacade transferFacade;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        transferFacade = new TransferFacade(accountRepository, transactionRepository);
        accountRepository.saveAll(prepareDummyAccounts());
    }

    @Test
    @DisplayName("process transfer - not found from account")
    void processTransfer_WithNotFoundFromAccountNumber() {
        assertThrows(ServiceException.class,
                () -> transferFacade.processTransfer(buildTransferRequestWithNotFoundFromAccount()));
    }

    @Test
    @DisplayName("process transfer - not found to account")
    void processTransfer_WithNotFoundToAccountNumber() {
        assertThrows(ServiceException.class,
                () -> transferFacade.processTransfer(buildTransferRequestWithNotFoundToAccount()));
    }

    @Test
    @DisplayName("process transfer - with zero balance")
    void processTransfer_WithZeroBalance() {
        assertThrows(ServiceException.class,
                () -> transferFacade.processTransfer(buildTransferRequestWithZeroBalanceAccount()));
    }

    @Test
    @DisplayName("process transfer - success")
    void processTransfer_success() {
        transferFacade.processTransfer(buildSuccessTransferRequest());
        Account fromAccount = accountRepository.findByNumber("123876536789134").get();
        Account toAccount = accountRepository.findByNumber("98763521415378446").get();
        assertEquals(950, fromAccount.getBalance());
        assertEquals(60.5, toAccount.getBalance());
        assertEquals(2, transactionRepository.count());
    }

    private TransferRequest buildTransferRequestWithNotFoundFromAccount() {
        return TransferRequest.builder()
                .fromAccount("987624145637345")
                .toAccount("123876536789134")
                .amount(50.0)
                .build();
    }

    private TransferRequest buildTransferRequestWithNotFoundToAccount() {
        return TransferRequest.builder()
                .fromAccount("123876536789134")
                .toAccount("986134678236452")
                .amount(50.0)
                .build();
    }

    private TransferRequest buildTransferRequestWithZeroBalanceAccount() {
        return TransferRequest.builder()
                .fromAccount("1234532415378446")
                .toAccount("123876536789134")
                .amount(50.0)
                .build();
    }

    private TransferRequest buildSuccessTransferRequest() {
        return TransferRequest.builder()
                .fromAccount("123876536789134")
                .toAccount("98763521415378446")
                .amount(50)
                .build();
    }

    private List<Account> prepareDummyAccounts() {

        Account acc1 = new Account();
        acc1.setBalance(1000);
        acc1.setNumber("123876536789134");
        acc1.setBankName("ADCB");
        acc1.setOwnerName("ABANOUB NASSER");

        Account acc2 = new Account();
        acc2.setBalance(10.5);
        acc2.setNumber("98763521415378446");
        acc2.setBankName("FAB");
        acc2.setOwnerName("MARK MAC");

        Account acc3 = new Account();
        acc3.setBalance(0);
        acc3.setNumber("1234532415378446");
        acc3.setBankName("NBD");
        acc3.setOwnerName("Massimo YO");

        return List.of(acc1, acc2, acc3);
    }
}
