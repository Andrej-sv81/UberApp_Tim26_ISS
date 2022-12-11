package com.example.demo.dto;

import java.util.List;

public class MultipleReviewsDTO {
    private int totalCount;
    private List<ReviewResponseDTO> results;

    public MultipleReviewsDTO() {
    }

    public MultipleReviewsDTO(int totalCount, List<ReviewResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<ReviewResponseDTO> getResults() {
        return results;
    }

    public void setResults(List<ReviewResponseDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MultipleReviewsDTO{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
