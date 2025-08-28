package dev.merez.paymentschedule.service;

import dev.merez.paymentschedule.adapter.dto.account.AccountDTO;
import dev.merez.paymentschedule.adapter.dto.account.CreateAccountDTO;
import dev.merez.paymentschedule.domain.Account;
import dev.merez.paymentschedule.persistance.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    // TODO: Implementar saque e débito das contas conforme o pagamento.

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO getAccount(String accountNumber) {
        Optional<Account> _account = accountRepository.findAccountByAccountNumber(accountNumber);
        Account account = _account.orElseThrow(() -> new NoSuchElementException("Conta não existe."));
        return AccountDTO.from(account);
    }

    public Account createAccount(CreateAccountDTO dto) {
        Long initialBalance = dto.getInitialBalance() != null ? dto.getInitialBalance() : 0L;
        Account conta = Account.of(generateAccountNumber(), initialBalance, dto.getNome());
        return accountRepository.save(conta);
    }

    private String generateAccountNumber() {
        String candidate;
        do {
            candidate = UUID.randomUUID().toString().toUpperCase().substring(0, 10);
        } while(accountRepository.existsAccountsByAccountNumber(candidate));

        return candidate;
    }
}
