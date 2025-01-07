package br.com.and.controller;

import br.com.and.model.Cambio;
import br.com.and.service.CambioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "Cambio Service API")
@RestController
@RequestMapping("cambio-service")
public class CambioController {

    private final Logger logger = LoggerFactory.getLogger(CambioController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private CambioService service;

    @Operation(summary = "Get cambio from currency")
    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(
            @PathVariable("amount")BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {
        logger.info("getCambio is called with -> {}, {} and {}", amount, from, to);

        String port = environment.getProperty("local.server.port");

        Cambio cambio = service.findCambio(new Cambio(from, to), amount);
        cambio.setEnvironment(port);

        return cambio;
    }
}
