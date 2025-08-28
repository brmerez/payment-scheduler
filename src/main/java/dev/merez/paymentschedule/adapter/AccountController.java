package dev.merez.paymentschedule.adapter;

import dev.merez.paymentschedule.adapter.dto.account.AccountDTO;
import dev.merez.paymentschedule.adapter.dto.account.CreateAccountDTO;
import dev.merez.paymentschedule.domain.Account;
import dev.merez.paymentschedule.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountNumber) {
        AccountDTO dto = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CreateAccountDTO accountDTO) {
        try {
            Account account = accountService.createAccount(accountDTO);
            AccountDTO dto = AccountDTO.from(account);
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
