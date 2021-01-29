package com.llifon.prime.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * The REST API controller for handling client requests for all of the prime numbers come before a given number.
 */
@RestController
public class PrimeNumberController {

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