package dev.merez.paymentschedule.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Account sender;

    @ManyToOne(optional = false)
    private Account recipient;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private Long tax;
    private LocalDateTime fulfilledAt;

    private Payment(Account sender, Account recipient, Long amount, LocalDateTime createdAt) {
        if(sender.equals(recipient)){
            throw new IllegalStateException("Não é possível transferir para mesma conta");
        }

        if(amount <= 0){
            throw new IllegalStateException("Valor do pagamento deve ser maior que 0");
        }

        if(sender.getBalance() < amount){
            throw new IllegalStateException("Saldo insuficiente");
        }

        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.createdAt = createdAt;
    }

    public static Payment create(Account sender, Account recipient, Long amount) {
        LocalDateTime now = LocalDateTime.now();
        return new Payment(sender, recipient, amount, now);
    }

    public void setTax(Long tax) {

        if(this.getSender().getBalance() < this.getTotal()){
            throw new IllegalStateException("Saldo insuficiente");
        }

        this.tax = tax;
    }

    public Long getTotal() {
        return amount + (tax == null ? 0 : tax);
    }

}
