package com.llifon.prime.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest
@Transactional
public class PrimeNumberServiceTests {

    @Autowired
    private PrimeNumberService service;

    /**
     * Tests that an IllegalArgumentException is raised when the UpTo argument is less than startingFrom
     */
    @Test
    void argumentExceptionWhenUpToLessThanStart() {
        service.RequestPagedPrimeNumbers(2, 2, 1, 1);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.RequestPagedPrimeNumbers(2, 1, 1, 1));
    }

    /**
     * Tests that an IllegalArgumentException is raised when the startingFrom argument is negative
     */
    @Test
    void argumentExceptionWhenStartIsNegative() {
        service.RequestPagedPrimeNumbers(0, 2, 1, 1);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.RequestPagedPrimeNumbers(-1, 1, 1, 1));
    }

    /**
     * Tests that an IllegalArgumentException is raised when the data argument is negative
     */
    @Test
    void argumentExceptionWhenViewSizeIsZeroOrNegative() {
        service.RequestPagedPrimeNumbers(2, 2, 1, 1);

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.RequestPagedPrimeNumbers(2, 2, 0, 1));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.RequestPagedPrimeNumbers(2, 2, -1, 1));
    }

    /**
     * Tests that an IllegalArgumentException is raised when the page argument is negative
     */
    @Test
    void argumentExceptionWhenPageIsNegative() {
        service.RequestPagedPrimeNumbers(2, 2, 1, 0);
        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.RequestPagedPrimeNumbers(2, 2, 1, -1));
    }

}
