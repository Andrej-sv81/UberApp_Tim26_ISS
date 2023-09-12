package com.example.demo.dto;

import java.util.List;

public class DriverReportDTO {

    private List<Integer> numberOfRides;
    private List<Integer> sumOfPrices;

    public DriverReportDTO(List<Integer> numberOfRides, List<Integer> sumOfPrices) {
        this.numberOfRides = numberOfRides;
        this.sumOfPrices = sumOfPrices;
    }

    public List<Integer> getNumberOfRides() {
        return numberOfRides;
    }

    public void setNumberOfRides(List<Integer> numberOfRides) {
        this.numberOfRides = numberOfRides;
    }

    public List<Integer> getSumOfPrices() {
        return sumOfPrices;
    }

    public void setSumOfPrices(List<Integer> sumOfPrices) {
        this.sumOfPrices = sumOfPrices;
    }
}
