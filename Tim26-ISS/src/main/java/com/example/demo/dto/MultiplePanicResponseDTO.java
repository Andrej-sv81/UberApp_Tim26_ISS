package com.example.demo.dto;

import java.util.List;

public class MultiplePanicResponseDTO {
    private Integer totalCount;

    private List<PanicResponseDTO> results;

    public MultiplePanicResponseDTO(){};

    public MultiplePanicResponseDTO(Integer totalCount, List<PanicResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<PanicResponseDTO> getResults() {
        return results;
    }

    public void setResults(List<PanicResponseDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MultiplePanicResponseDTO{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
