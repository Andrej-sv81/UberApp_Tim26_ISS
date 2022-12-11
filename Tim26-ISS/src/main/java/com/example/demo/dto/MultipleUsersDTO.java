package com.example.demo.dto;

import java.util.List;

public class MultipleUsersDTO {
    private int totalCount;
    private List<UserResponseDTO> results;

    public MultipleUsersDTO(){};

    public MultipleUsersDTO(int totalCount, List<UserResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<UserResponseDTO> getResults() {
        return results;
    }

    public void setResults(List<UserResponseDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MultipleUsersDTO{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
