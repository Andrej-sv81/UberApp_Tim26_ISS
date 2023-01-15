package com.example.demo.dto;

import com.example.demo.dto.user.UserResponseDTO;

import java.util.List;

public class MultipleDTO {
    private int totalCount;
    private List<?> results;

    public MultipleDTO(){};

    public MultipleDTO(int totalCount, List<?> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<?> getResults() {
        return results;
    }

    public void setResults(List<?> results) {
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
