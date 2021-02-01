package com.llifon.prime.backend;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * An implementation of {@link PrimeSequenceGenerator} which uses the inbuilt method of
 * {@link BigInteger#nextProbablePrime} to estimate the next prime number after a current value.
 * <p>
 * Note that this implementation is based on the Miller–Rabin primality test, which means it has
 * a really really slim chance of producing a false prime number.
 */
public class BigIntegerPrimeSequenceGenerator implements PrimeSequenceGenerator<BigInteger> {

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
    @Override
    public Iterator<BigInteger> generate(BigInteger from, BigInteger upTo, boolean inclusive) {

        if (from.compareTo(upTo) > 0)
        {
            throw new IllegalArgumentException(String.format("{from} (%s) must be less than or equal to {upTo} (%s)", from, upTo));
        }

        BigInteger tracker = from.subtract(BigInteger.ONE); // If from = 2, then first 'next prime' would be 3.

        LinkedList<BigInteger> results = new LinkedList<>();
        while (true) {
            tracker = tracker.nextProbablePrime();

            if (tracker.compareTo(upTo) < 0) {
                results.add(tracker);
            } else if (inclusive && tracker.compareTo(upTo) == 0) {
                results.add(tracker);
            } else {
                break;
            }
        }

        return results.iterator();
    }
}
