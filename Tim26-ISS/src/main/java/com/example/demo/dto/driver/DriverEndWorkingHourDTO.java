package com.example.demo.dto.driver;

public class DriverEndWorkingHourDTO {

    private String end;

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public DriverEndWorkingHourDTO() {
    }

    public DriverEndWorkingHourDTO(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "DriverEndWorkingHourDTO{" +
                "end='" + end + '\'' +
                '}';
    }
}
