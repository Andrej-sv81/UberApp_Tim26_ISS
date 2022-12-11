package com.example.demo.dto;

import com.example.demo.model.WorkingHour;

import java.util.ArrayList;
import java.util.List;

public class DriverWorkingHoursDTO {
    private int total;
    private List<WorkingHour> results;

    public DriverWorkingHoursDTO() {
        this.total = 0;
        this.results = new ArrayList<WorkingHour>();
    }

    public DriverWorkingHoursDTO(int total, List<WorkingHour> results) {
        this.total = total;
        this.results = results;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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
                "total=" + total +
                ", results=" + results +
                '}';
    }
}
