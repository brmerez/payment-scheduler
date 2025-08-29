package dev.merez.paymentschedule.service;

import dev.merez.paymentschedule.domain.Account;
import dev.merez.paymentschedule.domain.Payment;
import dev.merez.paymentschedule.domain.ScheduledPayment;
import dev.merez.paymentschedule.persistance.AccountRepository;
import dev.merez.paymentschedule.persistance.PaymentRepository;
import dev.merez.paymentschedule.persistance.ScheduledPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PaymentSchedulerService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ScheduledPaymentRepository scheduledPaymentRepository;

    @Autowired
    TaxService taxService;

    public List<ScheduledPayment> getScheduledPaymentsForSender(String senderAcc){

        if(!accountRepository.existsAccountsByAccountNumber(senderAcc)){
            throw new NoSuchElementException("Não foi possível achar a conta com código '" + senderAcc + "'");
        }

        return scheduledPaymentRepository.findByPaymentSenderAccountNumber(senderAcc);
    }

    public ScheduledPayment schedulePayment(String senderAcc, String receiverAcc, LocalDateTime scheduledTo, Long amount) {

        // Mock Data
        LocalDateTime scheduledAt = LocalDateTime.now();

        Optional<Account> _sender = accountRepository.findAccountByAccountNumber(senderAcc);
        Optional<Account> _receiver = accountRepository.findAccountByAccountNumber(receiverAcc);

        Account sender = _sender.orElseThrow(() -> new IllegalArgumentException("Conta do pagador não encontrada."));
        Account receiver = _receiver.orElseThrow(() -> new IllegalArgumentException("Conta do recebedor não encontrada."));
        Payment payment = Payment.create(sender, receiver, amount);

        ScheduledPayment schPayment = ScheduledPayment.schedulePayment(payment, scheduledAt, scheduledTo, taxService);

        scheduledPaymentRepository.save(schPayment);

        return schPayment;
    }


    public ScheduledPayment performPayment() {
        // TODO: Implementar pagamento?
        return null;
    }

    public Optional<ScheduledPayment> getPaymentById(Long id) {
        return scheduledPaymentRepository.findById(id);
    }

    public List<ScheduledPayment> getScheduledPaymentsForAccount(String accountNumber) {
        if (!accountRepository.existsAccountsByAccountNumber(accountNumber)) {
            throw new NoSuchElementException("Conta com número '" + accountNumber + "' não encontrada");
        }
        return scheduledPaymentRepository.findBySenderOrRecipientAccountNumber(accountNumber);
    }
}
