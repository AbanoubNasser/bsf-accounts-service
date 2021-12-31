package com.bsf.services.accountsservice.facades;

import com.bsf.services.accountsservice.api.bo.AccountData;
import com.bsf.services.accountsservice.exception.ServiceException;
import com.bsf.services.accountsservice.model.Account;
import com.bsf.services.accountsservice.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class AccountFacadeTest {

    private AccountFacade accountFacade;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        accountFacade = new AccountFacade(accountRepository);
        accountRepository.saveAll(prepareDummyAccounts());
    }

    @Test
    @DisplayName("find account data by number - not found")
    void findAccountData_NotFound() {
        assertThrows(ServiceException.class,
                () -> accountFacade.retrieveAccountByNumber("1122298264526718"));
    }

    @Test
    @DisplayName("find account data by number - success")
    void findAccountData_success() {
        AccountData accountData = accountFacade.retrieveAccountByNumber("123876536789134");
        assertNotNull(accountData);
        assertEquals("123876536789134", accountData.getNumber());
        assertEquals(1000, accountData.getBalance());
        assertEquals("ADCB", accountData.getBankName());
        assertEquals("ABANOUB NASSER", accountData.getOwnerName());
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

        return List.of(acc1, acc2);
    }
}
