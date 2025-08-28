package dev.merez.paymentschedule.adapter.dto.scheduledpayment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SchedulePaymentDTO {
    private String senderAccount;
    private String receiverAccount;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime scheduledTo;
    private Long amount;
}
