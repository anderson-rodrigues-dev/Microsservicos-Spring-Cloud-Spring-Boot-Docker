package br.com.and.controller;

import br.com.and.model.Book;
import br.com.and.proxy.CambioProxy;
import br.com.and.repository.BookRepository;
import br.com.and.response.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @Autowired
    private BookRepository repository;

    @Autowired
    private CambioProxy proxy;

    @Operation(summary = "Find a specific book by its ID")
    @GetMapping(value = "/{id}/{currency}")
    public Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        Book book = repository.findById(id).orElseThrow(() -> new RuntimeException("ID not found"));

        Cambio cambio = proxy.getCambio(book.getPrice(), "USD", currency);

        var port = environment.getProperty("local.server.port");
        book.setEnvironment("Book Port: " + port + "; Cambio Port: " + cambio.getEnvironment());
        book.setPrice(cambio.getConvertedValue());
        book.setCurrency(cambio.getTo());

        return book;
    }
}
