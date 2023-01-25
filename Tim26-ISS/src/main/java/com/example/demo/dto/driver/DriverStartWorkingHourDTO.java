package com.example.demo.dto.driver;

public class DriverStartWorkingHourDTO {

    private String start;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public DriverStartWorkingHourDTO(String start) {
        this.start = start;
    }

    public DriverStartWorkingHourDTO() {
    }

    @Override
    public String toString() {
        return "DriverStartWorkingHourDTO{" +
                "start='" + start + '\'' +
                '}';
    }
}
