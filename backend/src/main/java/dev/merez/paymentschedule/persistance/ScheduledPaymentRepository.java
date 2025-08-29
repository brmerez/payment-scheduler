package dev.merez.paymentschedule.persistance;

import dev.merez.paymentschedule.domain.ScheduledPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledPaymentRepository extends JpaRepository<ScheduledPayment, Long> {
     List<ScheduledPayment> findByPaymentSenderAccountNumber(String accountNumber);

     @Query("SELECT sp FROM ScheduledPayment sp " +
           "WHERE sp.payment.sender.accountNumber = :accountNumber " +
           "OR sp.payment.recipient.accountNumber = :accountNumber")
    List<ScheduledPayment> findBySenderOrRecipientAccountNumber(@Param("accountNumber") String accountNumber);
}
