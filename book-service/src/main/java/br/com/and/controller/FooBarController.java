package br.com.and.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Foo Bar")
@RestController
@RequestMapping("book-service")
public class FooBarController {

    private final Logger logger = LoggerFactory.getLogger(FooBarController.class);

    @Operation(summary = "Foo Bar")
    @GetMapping("/foo-bar")
    // @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
    // @CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String fooBar() {
        logger.info("Request to foobar is received!");
//        var response = new RestTemplate()
//                .getForEntity("http://localhost:8080/foo-bar", String.class);
//        return response.getBody();
        return "Foo-Bar!!";
    }

    public String fallbackMethod(Exception ex) {
        return "fallbackMethod foo-bar!!!";
    }
}
