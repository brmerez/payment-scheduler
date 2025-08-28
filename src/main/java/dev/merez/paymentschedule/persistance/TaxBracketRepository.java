package dev.merez.paymentschedule.persistance;

import dev.merez.paymentschedule.domain.TaxBracket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxBracketRepository extends JpaRepository<TaxBracket,Long> {
    @Query("SELECT t FROM TaxBracket t WHERE :days BETWEEN t.minDays AND t.maxDays")
    Optional<TaxBracket> findByDays(@Param("days") long days);
}
