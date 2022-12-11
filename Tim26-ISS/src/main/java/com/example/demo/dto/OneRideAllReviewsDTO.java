package com.example.demo.dto;

public class OneRideAllReviewsDTO {
    private ReviewResponseDTO vehicleReview;
    private ReviewResponseDTO driverReview;

    public OneRideAllReviewsDTO() {
    }

    public OneRideAllReviewsDTO(ReviewResponseDTO vehicleReview, ReviewResponseDTO driverReview) {
        this.vehicleReview = vehicleReview;
        this.driverReview = driverReview;
    }

    public ReviewResponseDTO getVehicleReview() {
        return vehicleReview;
    }

    public void setVehicleReview(ReviewResponseDTO vehicleReview) {
        this.vehicleReview = vehicleReview;
    }

    public ReviewResponseDTO getDriverReview() {
        return driverReview;
    }

    public void setDriverReview(ReviewResponseDTO driverReview) {
        this.driverReview = driverReview;
    }

    @Override
    public String toString() {
        return "OneRideAllReviewsDTO{" +
                "vehicleReview=" + vehicleReview +
                ", driverReview=" + driverReview +
                '}';
    }
}
