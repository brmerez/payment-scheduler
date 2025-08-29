package dev.merez.paymentschedule.adapter;

import dev.merez.paymentschedule.adapter.dto.PaymentException;
import dev.merez.paymentschedule.adapter.dto.scheduledpayment.SchedulePaymentDTO;
import dev.merez.paymentschedule.adapter.dto.scheduledpayment.ScheduledPaymentResultDTO;
import dev.merez.paymentschedule.domain.ScheduledPayment;
import dev.merez.paymentschedule.service.PaymentSchedulerService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    PaymentSchedulerService paymentScheduler;

    @GetMapping("/teste")
    public String getTeste(){
        return "OK!";
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable Long id){
        Optional<ScheduledPayment> match = paymentScheduler.getPaymentById(id);
        ScheduledPayment scheduledPayment = match.orElseThrow(() -> new NoSuchElementException("Payment not found"));
        return ResponseEntity.ok().body(ScheduledPaymentResultDTO.from(scheduledPayment));
    }

    @GetMapping()
    public ResponseEntity<?> getPayments(){
        return null;
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<?> getScheduledPaymentsByUser(@PathVariable String accountNumber) {
        try {
            List<ScheduledPayment> payments = paymentScheduler.getScheduledPaymentsForSender(accountNumber);
            List<ScheduledPaymentResultDTO> scheduledPayments = payments.stream().map(ScheduledPaymentResultDTO::from).collect(Collectors.toList());
            return ResponseEntity.ok(scheduledPayments);
        } catch(NoSuchElementException e){
            PaymentException pe = new PaymentException(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch(Exception e){
            PaymentException pe = new PaymentException(e.getMessage());
            return ResponseEntity.badRequest().body(pe);
        }
    }

    @PostMapping("/schedule")
    public ResponseEntity<?> schedulePayment(@RequestBody SchedulePaymentDTO schedulePaymentDTO){
        try {
            String senderAcc = schedulePaymentDTO.getSenderAccount();
            String receiverAcc = schedulePaymentDTO.getReceiverAccount();
            Long amount = schedulePaymentDTO.getAmount();
            LocalDateTime scheduledTo = schedulePaymentDTO.getScheduledTo();
            ScheduledPayment scheduledPayment = paymentScheduler.schedulePayment(senderAcc, receiverAcc, scheduledTo, amount);

            ScheduledPaymentResultDTO schPaymentResultDTO = ScheduledPaymentResultDTO.from(scheduledPayment);
            return ResponseEntity.ok().body(schPaymentResultDTO);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            PaymentException paymentException = new PaymentException(e.getMessage());
            return ResponseEntity.badRequest().body(paymentException);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            PaymentException paymentException = new PaymentException(e.getMessage());
            return ResponseEntity.badRequest().body(paymentException);
        } catch (Exception e){
            e.printStackTrace();
            PaymentException paymentException = new PaymentException(e.getMessage());
            return ResponseEntity.internalServerError().body(paymentException);
        }
    }
    @GetMapping("/account/{accountNumber}/all")
    public ResponseEntity<?> getScheduledPaymentsForAccount(@PathVariable String accountNumber) {
        try {
            List<ScheduledPayment> payments = paymentScheduler.getScheduledPaymentsForAccount(accountNumber);
            List<ScheduledPaymentResultDTO> result = payments.stream()
                    .map(ScheduledPaymentResultDTO::from)
                    .sorted(Comparator.comparing(ScheduledPaymentResultDTO::getScheduledTo))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            PaymentException pe = new PaymentException(e.getMessage());
            return ResponseEntity.badRequest().body(pe);
        }
    }

}
