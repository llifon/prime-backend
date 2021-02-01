package com.llifon.prime.backend;

import java.util.Iterator;

/**
 * An interface for a unit that can generate a sequence of prime numbers which exist within a number range.
 *
 * @param <T> The data-type of the prime numbers. Useful if you want to customize the data type to store smaller
 *            or larger numbers.
 */
public interface PrimeSequenceGenerator<T extends Number> {

    /**
     * Generates a sequence of PRIME NUMBERS which exist within a given value range.
     *
     * @param from      The first number in the range (always inclusive).
     * @param upTo      The last number in the range (inclusiveness based on {@code inclusive} flag)
     * @param inclusive If {@code true}, the {@code upTo} value will be included in the output
     *                  if it itself is a prime number. Otherwise only PRIME NUMBERS which are less than
     *                  the upper bound will be included.
     * @return An iterator for all of the numbers that were generated. An iterator is chosen here in-order to
     * support a sequence amount that's larger than the capacity of {@link Integer#MAX_VALUE} which could be
     * an issue if we were to return an ArrayList for example.
     *
     * @throws IllegalArgumentException {from} must be less than or equal to {upTo}
     */
    Iterator<T> generate(T from, T upTo, boolean inclusive);
}
