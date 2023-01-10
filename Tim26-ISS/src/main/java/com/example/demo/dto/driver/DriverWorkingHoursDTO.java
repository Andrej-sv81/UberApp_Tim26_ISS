package com.example.demo.dto.driver;

import com.example.demo.model.WorkingHour;

import java.util.ArrayList;
import java.util.List;

public class DriverWorkingHoursDTO {
    private int totalCount;
    private List<WorkingHour> results;

    public DriverWorkingHoursDTO() {
        this.totalCount = 0;
        this.results = new ArrayList<WorkingHour>();
    }

    public DriverWorkingHoursDTO(int total, List<WorkingHour> results) {
        this.totalCount = total;
        this.results = results;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int total) {
        this.totalCount = total;
    }

    public List<WorkingHour> getResults() {
        return results;
    }

    public void setResults(List<WorkingHour> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "DriverWorkingHoursDTO{" +
                "total=" + totalCount +
                ", results=" + results +
                '}';
    }
}
