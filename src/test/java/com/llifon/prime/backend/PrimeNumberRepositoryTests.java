package com.llifon.prime.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit/Integration tests for {@link PrimeNumberRepository}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class PrimeNumberRepositoryTests {

    @Autowired
    private PrimeNumberRepository sRepository;

    /**
     * Tests that the repository is actually auto-wired.
     */
    @Test
    void autowiredRepositoryIsNotNull() {
        assertThat(sRepository).isNotNull();
    }

    /**
     * Tests that the database is empty at this stage.
     */
    @Test
    void isEmptyByDefault() {
        var all = sRepository.findAll();
        assertThat(all).isEmpty();
    }

    /**
     * Tests that data can be stored and then retrieved.
     */
    @Test
    void testDataCanBeSavedThenRetrieved() {

        var first = new PrimeNumberEntity();
        first.setId(1L);
        first.setPrimeNumber(2L);

        var second = new PrimeNumberEntity();
        second.setId(2L);
        second.setPrimeNumber(3L);

        sRepository.save(first);
        sRepository.save(second);

        var all = sRepository.findAll(Sort.by(Sort.Order.asc("primeNumber")));
        var firstFromDb = all.get(0);
        var secondFromDb = all.get(1);

        assertThat(firstFromDb.getPrimeNumber()).isEqualTo(first.getPrimeNumber());
        assertThat(secondFromDb.getPrimeNumber()).isEqualTo(second.getPrimeNumber());
    }

    /**
     * Tests that the repository is able to return database data according to the page requests.
     */
    @Test
    void testRangeFunctionReturnsCorrectlyPagedData() {
        Long[] earlyPrimes = {
                2L, 3L,
                5L, 7L,
                11L};

        for (var ep : earlyPrimes) {
            var p = new PrimeNumberEntity();
            p.setPrimeNumber(ep);
            sRepository.save(p);
        }

        var pagedResult = sRepository.findPrimesInRange(PageRequest.of(0, 2), 1L, 10L);
        assertThat(new Long[]{2L, 3L}).containsExactlyElementsOf(pagedResult.getContent());

        pagedResult = sRepository.findPrimesInRange(PageRequest.of(0, 3), 1L, 10L);
        assertThat(new Long[]{2L, 3L, 5L}).containsExactlyElementsOf(pagedResult.getContent());

        pagedResult = sRepository.findPrimesInRange(PageRequest.of(0, 4), 1L, 10L);
        assertThat(new Long[]{2L, 3L, 5L, 7L}).containsExactlyElementsOf(pagedResult.getContent());

        pagedResult = sRepository.findPrimesInRange(PageRequest.of(1, 2), 1L, 10L);
        assertThat(new Long[]{5L, 7L}).containsExactlyElementsOf(pagedResult.getContent());

        pagedResult = sRepository.findPrimesInRange(PageRequest.of(2, 2), 1L, 11L);
        assertThat(new Long[]{11L}).containsExactlyElementsOf(pagedResult.getContent());
    }
}
