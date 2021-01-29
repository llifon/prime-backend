package com.llifon.prime.backend;

import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Generates a prime number sequence using the Sieve Of Eratosthenes algorithm.
 * Unlike {@link BigIntegerPrimeSequenceGenerator}, this algorithm does not come with the risk of producing
 * false positives.
 *
 * The implementation is copied from the base article below, but has been modified.
 * https://www.geeksforgeeks.org/java-program-for-sieve-of-eratosthenes/
 *
 * The main differences in the implementation is that we use a BitSet to track the flags, as it greatly
 * reduces the memory overhead of storing booleans (which can range from 4-8+ bytes each, depending on the JVM). With
 * that saving, the implementation still uses the Integer data-type, since the implementation type relies a-lot
 * on memory allocation, and will likely run into OutOfMemory issues if extended to Long or BigInt.
 *
 * Unit tests show that the performance of this implementation is much better than {@link BigIntegerPrimeSequenceGenerator},
 * the only difference being it consumes far more memory and is limited in its maximum range due to Integer and
 * memory constraints.
 */
public class SieveOfEratosthenesPrimeSequenceGenerator implements PrimeSequenceGenerator<Integer> {

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
    public Iterator<Integer> Generate(Integer from, Integer upTo, boolean inclusive) {

        if (from.compareTo(upTo) > 0)
        {
            throw new IllegalArgumentException(String.format("{from} (%s) must be less than or equal to {upTo} (%s)", from, upTo));
        }

        // Basically assumes everything is a PRIME number, and then goes through all enumerations crossing
        // out what we know for a fact IS NOT a prime number.
        BitSet prime = new BitSet(upTo - from);
        prime.set(0, (upTo - from) + 1, true);

        int upperLimit = inclusive ? upTo : upTo - 1;
        for(long p = from; p * p <= upperLimit; p++)
        {
            // If prime[p] is not changed, then it is a prime
            if(prime.get((int)p - from))
            {
                // Update all multiples of p
                for(long i = p * p; i <= upperLimit; i += p)
                {
                    prime.set((int)i - from, false);
                }
            }
        }

        List<Integer> c = new LinkedList<>();
        for(int i = from; i <= upperLimit; i++)
        {
            if(prime.get(i - from)) {
                c.add(i);
            }
        }

        return c.iterator();
    }
}
