package com.example.demo.dto;

import com.example.demo.dto.driver.DriverResponseDTO;

import java.util.List;

public class MultipleDriversDTO {

    private Integer totalCount;
    private List<DriverResponseDTO> results;

    public MultipleDriversDTO() {
    }

    public MultipleDriversDTO(Integer totalCount, List<DriverResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<DriverResponseDTO> getResults() {
        return results;
    }

    public void setResults(List<DriverResponseDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MultipleDriversDTO{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
