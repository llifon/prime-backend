package com.llifon.prime.backend;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

/**
 * The service for providing prime number sequences. While we could have technically used
 * Page and Pageable from Spring, this service exposing raw fields and a custom response seemed
 * better for reducing clutter and for decoupling CLIENT and SERVER implementations from Spring.
 */
@Service
public class PrimeNumberService {

    // The repository containing the known prime numbers
    private final PrimeNumberRepository repository;

    /**
     * Initializes a new instance of the prime number service
     *
     * @param repository The repository containing the known prime numbers
     */
    public PrimeNumberService(PrimeNumberRepository repository) {
        this.repository = repository;
    }

    /**
     * Gets the largest prime that this service can serve.
     *
     * @return The largest possible prime that can be provided by this service.
     */
    public long requestLargestSupportedPrime()
    {
        return this.repository.getLargestPrime();
    }

    /**
     * Generates a response containing prime numbers which exist within a given range.
     *
     * @param startingFrom   The start of the range from within which prime numbers should get sought.
     * @param upTo           The end of the range from within which prime numbers should get sought.
     * @param maxDataPerPage The maximum amount of data that should be provided on each page.
     * @param pageIndex      The index of the page whose data should be returned.
     * @return The official response containing the requested data
     */
    public PrimeNumberResponse requestPagedPrimeNumbers(long startingFrom,
                                                        long upTo,
                                                        int maxDataPerPage,
                                                        int pageIndex) {
        if (maxDataPerPage < 1) {
            throw new IllegalArgumentException("Requested page size must be > 0");
        }

        if (pageIndex < 0) {
            throw new IllegalArgumentException("Requested page index must be >= 0");
        }

        if (startingFrom < 0) {
            throw new IllegalArgumentException("from value must be >= 0");
        }

        if (upTo < startingFrom) {
            throw new IllegalArgumentException("'to' param must be > 'from' value");
        }

        var max = this.repository.getLargestPrime();
        if (upTo > max)
        {
            throw new IllegalArgumentException("This service does not support primes larger than " + max);
        }

        Pageable pageable = PageRequest.of(pageIndex, maxDataPerPage);

        var start = Instant.now();
        var repositoryResponse = this.repository.findPrimesInRange(pageable, startingFrom, upTo);
        var end = Instant.now();
        var pageImpl = new PageImpl<>(repositoryResponse.getContent(), pageable, repositoryResponse.getTotalElements());

        // Store meta-data for the original request
        PrimeNumberRequest request = new PrimeNumberRequest();
        request.setRequestStart(startingFrom);
        request.setRequestEnd(upTo);
        request.setPageIndex(pageIndex);
        request.setViewSize(maxDataPerPage);

        // Store meta-data for the pagination of the data
        PrimePageMetadata pageData = new PrimePageMetadata();
        pageData.setElementsOnPage(pageImpl.getNumberOfElements());
        pageData.setPageIndex(pageable.getPageNumber());
        pageData.setTotalElementsAvailable(pageImpl.getTotalElements());
        pageData.setTotalPages(pageImpl.getTotalPages());

        // Store the actual prime numbers and meta-data
        PrimeNumberResponse response = new PrimeNumberResponse();
        response.setPrimeNumbers(repositoryResponse.getContent());
        response.setRequestInfo(request);
        response.setPageInfo(pageData);
        response.setProcessingTime(Duration.between(start, end).toMillis());

        return response;
    }
}
