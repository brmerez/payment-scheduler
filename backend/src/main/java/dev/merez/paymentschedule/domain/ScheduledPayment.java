package dev.merez.paymentschedule.domain;


import dev.merez.paymentschedule.service.TaxService;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class ScheduledPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;

    @ManyToOne
    private TaxBracket taxBracket;

    private LocalDateTime scheduledAt;
    private LocalDateTime scheduledTo;
    private Boolean fulfilled;

    public static ScheduledPayment schedulePayment(Payment payment, LocalDateTime scheduledAt, LocalDateTime scheduledTo, TaxService taxService) {

        if(scheduledTo.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Data de pagamento inválida");
        }

        TaxBracket taxBracket = taxService.determineTaxBracket(scheduledAt, scheduledTo);
        Long taxes = taxService.calculateTax(payment.getAmount(), taxBracket);
        payment.setTax(taxes);

        ScheduledPayment scheduledPayment = new ScheduledPayment();
        scheduledPayment.setPayment(payment);
        scheduledPayment.setScheduledAt(scheduledAt);
        scheduledPayment.setScheduledTo(scheduledTo);
        scheduledPayment.setFulfilled(false);

        return scheduledPayment;
    }

}
