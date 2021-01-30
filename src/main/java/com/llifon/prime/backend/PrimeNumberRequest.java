package com.llifon.prime.backend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"from", "to", "pageIndex", "size"})
public class PrimeNumberRequest {

    // The start of the prime range
    private Long requestStart;

    // The end of the prime range
    private Long requestEnd;

    // The index of the page data requested
    private int pageIndex;

    // The maximum amount of items to show on a page
    private int viewSize;

    @JsonProperty("from")
    public Long getRequestStart() {
        return requestStart;
    }

    public void setRequestStart(Long requestStart) {
        this.requestStart = requestStart;
    }

    @JsonProperty("to")
    public Long getRequestEnd() {
        return requestEnd;
    }

    public void setRequestEnd(Long requestEnd) {
        this.requestEnd = requestEnd;
    }

    @JsonProperty("pageIndex")
    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @JsonProperty("size")
    public int getViewSize() {
        return viewSize;
    }

    public void setViewSize(int viewSize) {
        this.viewSize = viewSize;
    }
}
