package dev.merez.paymentschedule.service;

import dev.merez.paymentschedule.domain.TaxBracket;
import dev.merez.paymentschedule.persistance.TaxBracketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TaxService {

    @Autowired
    TaxBracketRepository taxBracketRepository;
    //TODO: Implementar criacão de novas faixas de taxa

    public TaxBracket determineTaxBracket(LocalDateTime scheduledAt, LocalDateTime scheduledTo) {

        long interval = ChronoUnit.DAYS.between(scheduledAt, scheduledTo);
        Optional<TaxBracket> taxBracket = taxBracketRepository.findByDays(interval);

        if(taxBracket.isEmpty()){
            Long maxDays = taxBracketRepository.findLargestMaxDays();
            throw new IllegalArgumentException("Não é possível agendar um pagamento para mais de " + maxDays + " dias.");
        }

        return taxBracket.get();
    }

    public Long calculateTax(Long amount, TaxBracket taxBracket) {
        double result = amount * taxBracket.getPercentageRate() / 100;
        result += taxBracket.getFixedRate();
        return Math.round(result);
    }
    public List<TaxBracket> getCurrentTaxBrackets() {
        return taxBracketRepository.findAllOrdered();
    }
}
