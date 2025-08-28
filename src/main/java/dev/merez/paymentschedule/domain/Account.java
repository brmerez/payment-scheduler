package dev.merez.paymentschedule.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false,   length = 10)
    private String accountNumber;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Long balance;

    private Account(String accountNumber, Long balance, String nome) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.nome = nome;
    }

    public static Account of(String accountNumber, Long balance, String nome) {

        if(balance == null) throw new IllegalArgumentException("balance cannot be null");
        if(accountNumber == null) throw new IllegalArgumentException("accountNumber cannot be null");
        if(accountNumber.isEmpty()) throw new IllegalArgumentException("accountNumber cannot be empty");
        if(accountNumber.length() != 10) throw new IllegalArgumentException("accountNumber length is not 10");
        if(nome == null || nome.isEmpty()) throw new IllegalArgumentException("nome cannot be empty");

        return new Account(accountNumber, balance, nome);
    }

}
