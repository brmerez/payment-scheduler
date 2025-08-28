package dev.merez.paymentschedule.adapter.dto.account;

import dev.merez.paymentschedule.domain.Account;
import lombok.Data;

@Data
public class AccountDTO {
    private String account;
    private String name;

    public static AccountDTO from(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccount(account.getAccountNumber());
        dto.setName(account.getNome());
        return dto;

    }
}
