package com.llifon.prime.backend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * A JPA repository which communicates with the table that's associated with the {@link PrimeNumberEntity} entity.
 *
 * Spring-Boot will auto implement this repository and we don't need to do any further work to use it.
 */
public interface PrimeNumberRepository extends JpaRepository<PrimeNumberEntity, Long> {

    /**
     * Retrieves paginated data from the data source containing the prime numbers which fall within the
     * given range (inclusive).
     *
     * @param pageable The pageable meta-data object indicating how much data is required in the output page.
     *
     * @param start The lowest (inclusive) value of the range.
     * @param end The largest (inclusive) value of the range.
     *
     * @return All known prime numbers which exist within the given value range. The amount of data
     * returned is conditional on the paginated request.
     */
    @Query("SELECT x.primeNumber FROM PrimeNumberEntity x WHERE x.primeNumber >= ?1 and x.primeNumber <= ?2 ORDER BY x.primeNumber ASC")
    Page<Long> findPrimesInRange(Pageable pageable, Long start, Long end);

    /**
     * Gets the largest prime number in the repository.
     *
     * @return The largest prime value.
     */
    @Query("SELECT max(x.primeNumber) FROM PrimeNumberEntity x")
    Long getLargestPrime();
}
