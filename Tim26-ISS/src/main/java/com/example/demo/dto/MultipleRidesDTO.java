package com.example.demo.dto;

import com.example.demo.dto.ride.RideDTO;

import java.util.List;

public class MultipleRidesDTO {
    private Integer totalCount;
    private List<RideDTO> results;

    public MultipleRidesDTO(){};

    public MultipleRidesDTO(Integer totalCount, List<RideDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<RideDTO> getResults() {
        return results;
    }

    public void setResults(List<RideDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MultipleRidesDTO{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
