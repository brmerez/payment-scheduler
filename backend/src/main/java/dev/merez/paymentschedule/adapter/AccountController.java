package dev.merez.paymentschedule.adapter;

import dev.merez.paymentschedule.adapter.dto.account.AccountDTO;
import dev.merez.paymentschedule.adapter.dto.account.CreateAccountDTO;
import dev.merez.paymentschedule.adapter.dto.account.CreateAccountReturnDTO;
import dev.merez.paymentschedule.domain.Account;
import dev.merez.paymentschedule.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping()
    public ResponseEntity<?> getAccounts() {
        try {
            List<Account> dto = accountService.getAccounts();
            List<AccountDTO> dtoList = dto.stream().map(AccountDTO::from).collect(Collectors.toList());
            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"Erro ao buscar contatos\" }");
        }
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountNumber) {
        AccountDTO dto = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CreateAccountReturnDTO> createAccount(@RequestBody CreateAccountDTO accountDTO) {
        try {
            Account account = accountService.createAccount(accountDTO);
            CreateAccountReturnDTO dto = new CreateAccountReturnDTO(account.getAccountNumber(), account.getNome(), account.getBalance());
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
