package com.llifon.prime.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"pageIndex", "primesInPage", "totalPrimesInRange", "totalPages"})
public class PrimePageMetadata {

    // The total number of pages available for the prime range
    private int totalPages;

    // The total number of prime elements currently shown on the page
    private long elementsOnPage;

    // The total number of primes available across all pages
    private long totalElementsAvailable;

    // The current page index being shown.
    private int pageIndex;

    @JsonProperty("totalPages")
    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @JsonProperty("primesInPage")
    public long getElementsOnPage() {
        return elementsOnPage;
    }

    public void setElementsOnPage(long elementsOnPage) {
        this.elementsOnPage = elementsOnPage;
    }

    @JsonProperty("totalPrimesInRange")
    public long getTotalElementsAvailable() {
        return totalElementsAvailable;
    }

    public void setTotalElementsAvailable(long totalElementsAvailable) {
        this.totalElementsAvailable = totalElementsAvailable;
    }

    @JsonProperty("pageIndex")
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
