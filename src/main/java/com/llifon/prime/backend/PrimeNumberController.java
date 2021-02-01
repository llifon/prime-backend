package com.llifon.prime.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 * The REST API controller for handling client requests for all of the prime numbers come before a given number.
 */
@RestController
public class PrimeNumberController {

    // The URL segment for generating prime number ranges.
    private static final String PRIME_API = "/prime-range";

    // the class logger
    private static final Logger logger = LoggerFactory.getLogger(PrimeNumberController.class);

    // The service model for generating prime number data.
    private final PrimeNumberService service;

    /**
     * Initializes a new Prime Number REST API controller.
     *
     * @param service The number service.
     */
    public PrimeNumberController(PrimeNumberService service) {
        this.service = service;
    }

    /**
     * The response at the base URL.
     *
     * @return N/A
     */
    @GetMapping("/")
    public String index() {
        return String.format("Get started by going to %s?from=2&to=100&page=0&size=10", PRIME_API);
    }

    @GetMapping(value = PRIME_API, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "*") // TODO: Remove this in production
    public PrimeNumberResponse providePrimeSequence(@RequestParam(name = "from", defaultValue = "2") Long from,
                                                    @RequestParam(name = "to") Long to,
                                                    @RequestParam(name = "page", defaultValue = "0") Long page,
                                                    @RequestParam(name = "size", defaultValue = "100") Long size) {

        var ret = this.service.RequestPagedPrimeNumbers(from, to, size.intValue(), page.intValue());
        logger.info("Serving client request of {}", ret.getRequestInfo());
        return ret;
    }
}
