package com.llifon.prime.backend;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatNoException;

public class BigIntegerPrimeSequenceGeneratorTests {

    /**
     * Tests that the FINAL value in a sequence is the same as the {@code upTo} parameter, if the value
     * itself is a prime number and the {@code inclusive} flag is set to {@code true}.
     */
    @Test
    public void includesFinalPrimeWhenUpToValueIsPrimeAndInclusiveSetToTrue() {
        BigIntegerPrimeSequenceGenerator generator = new BigIntegerPrimeSequenceGenerator();
        var primes = generator.generate(BigInteger.TWO, BigInteger.valueOf(13), true);

        BigInteger[] earlyPrimes = {
                BigInteger.valueOf(2), BigInteger.valueOf(3),
                BigInteger.valueOf(5), BigInteger.valueOf(7),
                BigInteger.valueOf(11), BigInteger.valueOf(13)};

        List<BigInteger> actualList = new ArrayList<>();
        primes.forEachRemaining(actualList::add);

        assertThat(earlyPrimes).containsExactlyElementsOf(actualList);
    }

    /**
     * Tests that the FINAL value in a sequence is less than the {@code upTo} parameter, if the value
     * itself is a not prime number and the {@code inclusive} flag is set to {@code true}.
     */
    @Test
    public void omitFinalPrimeWhenUpToValueIsPrimeAndInclusiveSetToTrue() {
        BigIntegerPrimeSequenceGenerator generator = new BigIntegerPrimeSequenceGenerator();
        var primes = generator.generate(BigInteger.TWO, BigInteger.valueOf(14), true);

        BigInteger[] earlyPrimes = {
                BigInteger.valueOf(2), BigInteger.valueOf(3),
                BigInteger.valueOf(5), BigInteger.valueOf(7),
                BigInteger.valueOf(11), BigInteger.valueOf(13)};

        List<BigInteger> actualList = new ArrayList<>();
        primes.forEachRemaining(actualList::add);

        assertThat(earlyPrimes).containsExactlyElementsOf(actualList);
    }

    /**
     * Tests that the FINAL value in a sequence is less than the {@code upTo} parameter, if the value
     * itself is a prime number but the {@code inclusive} flag is set to {@code false}.
     */
    @Test
    public void omitFinalPrimeWhenUpToValueIsPrimeAndInclusiveSetToFalse() {
        BigIntegerPrimeSequenceGenerator generator = new BigIntegerPrimeSequenceGenerator();
        var primes = generator.generate(BigInteger.TWO, BigInteger.valueOf(13), false);

        BigInteger[] earlyPrimes = {
                BigInteger.valueOf(2), BigInteger.valueOf(3),
                BigInteger.valueOf(5), BigInteger.valueOf(7),
                BigInteger.valueOf(11)};

        List<BigInteger> actualList = new ArrayList<>();
        primes.forEachRemaining(actualList::add);

        assertThat(earlyPrimes).containsExactlyElementsOf(actualList);
    }

    /**
     * Tests that a {@link IllegalArgumentException} is thrown if the FROM value is larger than the UPTO value.
     */
    @Test
    public void exceptionThrownIfFromIsLargerThanUpTo()
    {
        BigIntegerPrimeSequenceGenerator generator = new BigIntegerPrimeSequenceGenerator();

        assertThatThrownBy(() -> generator.generate(BigInteger.TWO, BigInteger.ONE, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("{from} (2) must be less than or equal to {upTo} (1)");
    }

    /**
     * Tests that no {@link IllegalArgumentException} is thrown if the FROM value is equal to the UPTO value.
     */
    @Test
    public void exceptionNotThrownIfFromEqualsUpTo()
    {
        BigIntegerPrimeSequenceGenerator generator = new BigIntegerPrimeSequenceGenerator();

        assertThatNoException().isThrownBy(() -> generator.generate(BigInteger.TWO, BigInteger.TWO, true));
        assertThatNoException().isThrownBy(() -> generator.generate(BigInteger.TWO, BigInteger.TWO, false));
    }

    /**
     * Tests the performance of the implementation.
     *
     * Took 3457ms to generate this sequence of a 5600X CPU.
     */
    @Test
    public void measurePerformance()
    {
        Instant now = Instant.now();

        BigIntegerPrimeSequenceGenerator generator = new BigIntegerPrimeSequenceGenerator();
        generator.generate(BigInteger.TWO, BigInteger.valueOf(1000000), true);
        Instant end = Instant.now();

        System.out.printf("BigInteger implementation took %d ms to execute 1 range of 2 - 1,000,000",
                Duration.between(now, end).toMillis());
    }
}
