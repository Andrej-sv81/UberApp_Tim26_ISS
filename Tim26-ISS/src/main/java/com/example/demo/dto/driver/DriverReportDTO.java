package com.example.demo.dto.driver;

public class DriverReportDTO {

    private int[] monthlyRides;
    private int[] monthlyEarnings;

    public DriverReportDTO(int[] monthlyRides, int[] monthlyEarnings) {
        this.monthlyRides = monthlyRides;
        this.monthlyEarnings = monthlyEarnings;
    }

    public DriverReportDTO(){
        this.monthlyRides = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        this.monthlyEarnings = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    }

    public int[] getMonthlyRides() {
        return monthlyRides;
    }

    public void setMonthlyRides(int[] monthlyRides) {
        this.monthlyRides = monthlyRides;
    }

    public int[] getMonthlyEarnings() {
        return monthlyEarnings;
    }

    public void setMonthlyEarnings(int[] monthlyEarnings) {
        this.monthlyEarnings = monthlyEarnings;
    }
}