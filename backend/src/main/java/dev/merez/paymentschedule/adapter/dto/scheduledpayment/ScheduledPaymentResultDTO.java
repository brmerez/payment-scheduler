package dev.merez.paymentschedule.adapter.dto.scheduledpayment;

import dev.merez.paymentschedule.adapter.dto.account.AccountDTO;
import dev.merez.paymentschedule.domain.Payment;
import dev.merez.paymentschedule.domain.ScheduledPayment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduledPaymentResultDTO {
    private Long id;
    private AccountDTO senderAccount;
    private AccountDTO recipientAccount;
    private Long amount;
    private Long taxes;
    private Long totalAmount;
    private LocalDateTime scheduledAt;
    private LocalDateTime scheduledTo;
    private boolean fulfilled;

    public static ScheduledPaymentResultDTO from(ScheduledPayment schPayment){
        ScheduledPaymentResultDTO dto = new ScheduledPaymentResultDTO();

        Payment payment = schPayment.getPayment();
        AccountDTO sender = AccountDTO.from(payment.getSender());
        AccountDTO receiver = AccountDTO.from(payment.getRecipient());

        dto.setId(payment.getId());
        dto.setSenderAccount(sender);
        dto.setRecipientAccount(receiver);
        dto.setScheduledAt(schPayment.getScheduledAt());
        dto.setScheduledTo(schPayment.getScheduledTo());
        dto.setAmount(payment.getAmount());
        dto.setTaxes(payment.getTax());
        dto.setTotalAmount(payment.getTotal());
        dto.setFulfilled(schPayment.getFulfilled());

        return dto;
    }

}

