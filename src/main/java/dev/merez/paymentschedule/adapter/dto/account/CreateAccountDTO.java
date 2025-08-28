package dev.merez.paymentschedule.adapter.dto.account;

import lombok.Data;

@Data
public class CreateAccountDTO {
    String nome;
    Long initialBalance;
}
