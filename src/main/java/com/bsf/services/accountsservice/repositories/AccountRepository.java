package com.bsf.services.accountsservice.repositories;

import com.bsf.services.accountsservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String accountNumber);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account acc SET acc.balance=acc.balance+ :amount WHERE acc.number = :accountNumber")
    int updateAccountBalance(@Param("amount") double amount, @Param("accountNumber") String accountNumber);
}
