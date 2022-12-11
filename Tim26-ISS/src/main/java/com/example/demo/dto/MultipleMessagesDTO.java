package com.example.demo.dto;

import java.util.List;

public class MultipleMessagesDTO {
    private Integer totalCount;
    List<UserNoteResponseDTO> results;

    public MultipleMessagesDTO(){};

    public MultipleMessagesDTO(Integer totalCount, List<UserNoteResponseDTO> results) {
        this.totalCount = totalCount;
        this.results = results;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<UserNoteResponseDTO> getResults() {
        return results;
    }

    public void setResults(List<UserNoteResponseDTO> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MultipleMessagesDTO{" +
                "totalCount=" + totalCount +
                ", results=" + results +
                '}';
    }
}
