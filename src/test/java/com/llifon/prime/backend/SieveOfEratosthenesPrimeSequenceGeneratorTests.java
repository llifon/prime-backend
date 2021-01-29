package com.llifon.prime.backend;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class SieveOfEratosthenesPrimeSequenceGeneratorTests {

    /**
     * Tests that the FINAL value in a sequence is the same as the {@code upTo} parameter, if the value
     * itself is a prime number and the {@code inclusive} flag is set to {@code true}.
     */
    @Test
    public void includesFinalPrimeWhenUpToValueIsPrimeAndInclusiveSetToTrue() {
        SieveOfEratosthenesPrimeSequenceGenerator generator = new SieveOfEratosthenesPrimeSequenceGenerator();
        var primes = generator.Generate(2, 13, true);

        Integer[] earlyPrimes = {
                2, 3,
                5, 7,
                11, 13};

        List<Integer> actualList = new ArrayList<>();
        primes.forEachRemaining(actualList::add);

        assertThat(earlyPrimes).containsExactlyElementsOf(actualList);
    }

    /**
     * Tests that the FINAL value in a sequence is less than the {@code upTo} parameter, if the value
     * itself is a not prime number and the {@code inclusive} flag is set to {@code true}.
     */
    @Test
    public void omitFinalPrimeWhenUpToValueIsPrimeAndInclusiveSetToTrue() {
        SieveOfEratosthenesPrimeSequenceGenerator generator = new SieveOfEratosthenesPrimeSequenceGenerator();
        var primes = generator.Generate(2, 14, true);

        Integer[] earlyPrimes = {
                2, 3,
                5, 7,
                11, 13};

        List<Integer> actualList = new ArrayList<>();
        primes.forEachRemaining(actualList::add);

        assertThat(earlyPrimes).containsExactlyElementsOf(actualList);
    }

    /**
     * Tests that the FINAL value in a sequence is less than the {@code upTo} parameter, if the value
     * itself is a prime number but the {@code inclusive} flag is set to {@code false}.
     */
    @Test
    public void omitFinalPrimeWhenUpToValueIsPrimeAndInclusiveSetToFalse() {
        SieveOfEratosthenesPrimeSequenceGenerator generator = new SieveOfEratosthenesPrimeSequenceGenerator();
        var primes = generator.Generate(2, 13, false);

        Integer[] earlyPrimes = {
                2, 3,
                5, 7,
                11};

        List<Integer> actualList = new ArrayList<>();
        primes.forEachRemaining(actualList::add);

        assertThat(earlyPrimes).containsExactlyElementsOf(actualList);
    }

    /**
     * Tests that a {@link IllegalArgumentException} is thrown if the FROM value is larger than the UPTO value.
     */
    @Test
    public void exceptionThrownIfFromIsLargerThanUpTo() {
        SieveOfEratosthenesPrimeSequenceGenerator generator = new SieveOfEratosthenesPrimeSequenceGenerator();

        assertThatThrownBy(() -> generator.Generate(2, 1, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("{from} (2) must be less than or equal to {upTo} (1)");
    }

    /**
     * Tests that no {@link IllegalArgumentException} is thrown if the FROM value is equal to the UPTO value.
     */
    @Test
    public void exceptionNotThrownIfFromEqualsUpTo() {
        SieveOfEratosthenesPrimeSequenceGenerator generator = new SieveOfEratosthenesPrimeSequenceGenerator();

        assertThatNoException().isThrownBy(() -> generator.Generate(2, 2, true));
        assertThatNoException().isThrownBy(() -> generator.Generate(2, 2, false));
    }

    /**
     * Tests the performance of the implementation.
     * <p>
     * Took 17ms to generate this sequence of a 5600X CPU.
     */
    @Test
    public void measurePerformance() {
        Instant now = Instant.now();

        SieveOfEratosthenesPrimeSequenceGenerator generator = new SieveOfEratosthenesPrimeSequenceGenerator();
        generator.Generate(2, 1000000, true);
        Instant end = Instant.now();

        System.out.printf("Integer implementation took %d ms to execute 1 range of 2 - 1,000,000",
                Duration.between(now, end).toMillis());
    }
}
