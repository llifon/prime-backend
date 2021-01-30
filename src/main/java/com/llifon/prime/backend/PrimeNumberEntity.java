package com.llifon.prime.backend;

import javax.persistence.*;

/**
 * Database entity class for storing prime numbers.
 *
 * @TODO Add 'IS_PRIME' property, so we can easily look-up if a value is a prime number or not.
 */
@Entity
@Table(name = "known_primes")
public class PrimeNumberEntity {

    // The backing field for the ID
    private Long id;

    // The backing field for the prime number
    private Long primeNumber;

    /**
     * Gets the prime number value.
     *
     * @return The prime number.
     */
    public Long getPrimeNumber() {
        return primeNumber;
    }

    /**
     * Sets the prime number value.
     *
     * @param primeNumber The prime number.
     */
    public void setPrimeNumber(Long primeNumber) {
        this.primeNumber = primeNumber;
    }

    /**
     * Gets the unique db ID.
     *
     * @param id The ID.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the unique db ID.
     *
     * @return The db ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
}
