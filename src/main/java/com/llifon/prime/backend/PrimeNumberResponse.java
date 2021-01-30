package com.llifon.prime.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"primeNumbers", "originalRequest", "pagination", "processingTime"})
public class PrimeNumberResponse {

    // A copy of the request criteria
    private PrimeNumberRequest requestInfo;

    // A copy of the pagination meta-data
    private PrimePageMetadata pageInfo;

    // The amount of time it took to generate a response
    private long processingTime;

    // The prime numbers which are valid for the current page
    private List<Long> primeNumbers;

    public List<Long> getPrimeNumbers() {
        return primeNumbers;
    }

    @JsonProperty("processingTime")
    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }

    @JsonProperty("primeNumbers")
    public void setPrimeNumbers(List<Long> primeNumbers) {
        this.primeNumbers = primeNumbers;
    }

    @JsonProperty("originalRequest")
    public PrimeNumberRequest getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(PrimeNumberRequest requestInfo) {
        this.requestInfo = requestInfo;
    }

    @JsonProperty("pagination")
    public PrimePageMetadata getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PrimePageMetadata pageInfo) {
        this.pageInfo = pageInfo;
    }
}
