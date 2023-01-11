package com.example.demo.dto;

import com.example.demo.dto.passenger.PassengerResponseDTO;

import java.util.List;

public class MultiplePassengersDTO {
    private int totalCount;
    private List<PassengerResponseDTO> results;

    public MultiplePassengersDTO() {
    }

    public MultiplePassengersDTO(int totalCount, List<PassengerResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<PassengerResponseDTO> getResults() {
        return results;
    }

    public void setResults(List<PassengerResponseDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MultiplePassengersDTO{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
