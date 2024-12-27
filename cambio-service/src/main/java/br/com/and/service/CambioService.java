package br.com.and.service;

import br.com.and.model.Cambio;
import br.com.and.repository.CambioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CambioService {
    @Autowired
    private CambioRepository repository;

    public Cambio findCambio(Cambio cambio, BigDecimal amount) {
        Cambio cambioSearched = repository.findByFromAndTo(cambio.getFrom(), cambio.getTo());
        if(cambioSearched == null) throw new RuntimeException("Currency Unsupported");

        BigDecimal convertedValue = cambioSearched.getConversionFactor().multiply(amount);

        cambioSearched.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));

        return cambioSearched;
    }
}
