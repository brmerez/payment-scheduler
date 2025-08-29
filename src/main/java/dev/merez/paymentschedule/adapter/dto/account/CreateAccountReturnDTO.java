package dev.merez.paymentschedule.adapter.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAccountReturnDTO {
    private String accountNumber;
    private String nome;
    private Long balance;
}
