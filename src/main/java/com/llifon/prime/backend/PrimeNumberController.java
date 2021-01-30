package com.llifon.prime.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * The REST API controller for handling client requests for all of the prime numbers come before a given number.
 */
@RestController
public class PrimeNumberController {

    // the class logger
    private static final Logger logger = LoggerFactory.getLogger(PrimeNumberController.class);

    /**
     * Default constructor.
     */
    public PrimeNumberController() {
    }

    /**
     * The response at the base URL.
     *
     * @return N/A
     */
    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}
