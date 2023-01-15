package com.example.demo.dto.user;

public class UnregisteredResponseDTO {

    private double estimatedTimeInMinutes;
    private double estimatedCost;

    public UnregisteredResponseDTO(){};

    public UnregisteredResponseDTO(double estimatedTimeInMinutes, double estimatedCost) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
        this.estimatedCost = estimatedCost;
    }

    public double getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public void setEstimatedTimeInMinutes(double estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    @Override
    public String toString() {
        return "UnregisteredResponseDTO{" +
                "estimatedTimeInMinutes=" + estimatedTimeInMinutes +
                ", estimatedCost=" + estimatedCost +
                '}';
    }
}
