package com.example.demo.dto.user;

import java.util.List;

public class UserMessagesResponseDTO {
    private Integer totalCount;
    private List<UserMessageResponseDTO> results;

    public UserMessagesResponseDTO(){};
    public UserMessagesResponseDTO(Integer totalCount, List<UserMessageResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<UserMessageResponseDTO> getResults() {
        return results;
    }

    public void setResults(List<UserMessageResponseDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "UserMessagesResponseDTO{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
