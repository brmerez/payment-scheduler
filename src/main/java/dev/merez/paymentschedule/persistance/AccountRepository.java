package dev.merez.paymentschedule.persistance;

import dev.merez.paymentschedule.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByAccountNumber(String number);
    Boolean existsAccountsByAccountNumber(String accountNumber);
}
