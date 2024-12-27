package br.com.and.controller;

import br.com.and.model.Cambio;
import br.com.and.service.CambioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("cambio-service")
public class CambioController {

    @Autowired
    private Environment environment;

    @Autowired
    private CambioService service;

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Cambio getCambio(
            @PathVariable("amount")BigDecimal amount,
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ) {
        String port = environment.getProperty("local.server.port");

        Cambio cambio = service.findCambio(new Cambio(from, to), amount);
        cambio.setEnvironment(port);

        return cambio;
    }
}
