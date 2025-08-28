package dev.merez.paymentschedule.adapter;

import dev.merez.paymentschedule.adapter.dto.PaymentException;
import dev.merez.paymentschedule.adapter.dto.scheduledpayment.SchedulePaymentDTO;
import dev.merez.paymentschedule.adapter.dto.scheduledpayment.ScheduledPaymentResultDTO;
import dev.merez.paymentschedule.domain.ScheduledPayment;
import dev.merez.paymentschedule.service.PaymentSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController()
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    PaymentSchedulerService paymentScheduler;

    @GetMapping("/teste")
    public String getTeste(){
        return "OK!";
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
        } catch (Exception e){
            e.printStackTrace();
            PaymentException paymentException = new PaymentException(e.getMessage());
            return ResponseEntity.internalServerError().body(paymentException);
        }
    }

}
