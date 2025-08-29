package dev.merez.paymentschedule.adapter;

import dev.merez.paymentschedule.domain.TaxBracket;
import dev.merez.paymentschedule.service.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tax-brackets")
public class TaxController {

    @Autowired
    private TaxService taxService;

    @GetMapping
    public ResponseEntity<List<TaxBracket>> getAllBrackets() {
        List<TaxBracket> brackets = taxService.getCurrentTaxBrackets();
        return ResponseEntity.ok(brackets);
    }

}
