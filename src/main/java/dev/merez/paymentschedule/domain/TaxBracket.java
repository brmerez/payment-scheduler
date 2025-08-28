package dev.merez.paymentschedule.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TaxBracket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long minDays;
    private long maxDays;
    private double fixedRate;
    private double percentageRate;

    private TaxBracket(Integer fixedRate, Double percentageRate) {
        this.fixedRate = fixedRate;
        this.percentageRate = percentageRate;
    }

    public static TaxBracket from(Integer fixedRate, Double percentageRate) {
        if(fixedRate == null) throw new IllegalArgumentException("FixedRate cannot be null");
        if( percentageRate == null) throw new IllegalArgumentException("PercentageRate cannot be null");

        if(fixedRate < 0) throw new IllegalArgumentException("fixedRate cannot be negative");
        if(percentageRate < 0) throw new IllegalArgumentException("percentageRate cannot be negative");
        if(percentageRate > 100) throw new IllegalArgumentException("percentageRate cannot be greater than 100");

        return new TaxBracket(fixedRate, percentageRate);
    }

}
