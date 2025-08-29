package dev.merez.paymentschedule.adapter.dto.account;

import dev.merez.paymentschedule.domain.Account;
import lombok.Data;

@Data
public class AccountDTO {
    private String accountNumber;
    private String nome;

    public static AccountDTO from(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setNome(account.getNome());
        return dto;

    }
}
