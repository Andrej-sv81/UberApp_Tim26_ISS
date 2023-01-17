package com.example.demo.dto;

import com.example.demo.dto.passenger.PassengerRideOverDTO;
import com.example.demo.model.Passenger;
import com.example.demo.model.Review;

public class ReviewResponseDTO {
    private Integer id;
    private int rating;
    private String comment;
    private PassengerRideOverDTO passenger;

    public ReviewResponseDTO() {
        this.passenger = new PassengerRideOverDTO();
    }

    public ReviewResponseDTO(Integer id, int rating, String comment, PassengerRideOverDTO passenger) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.passenger = passenger;
    }

    public ReviewResponseDTO(Review review, Passenger passenger) {
        this.id = review.getId();
        this.rating = review.getScore();
        this.comment = review.getComment();
        this.passenger = new PassengerRideOverDTO(passenger.getId(), passenger.getEmail());
    }

    public ReviewResponseDTO(Review review) {
        this.id = review.getId();
        this.rating = review.getScore();
        this.comment = review.getComment();
        this.passenger = new PassengerRideOverDTO(review.getPassenger().getId(), review.getPassenger().getEmail());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PassengerRideOverDTO getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerRideOverDTO passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "ReviewResponseDTO{" +
                "id=" + id +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", passenger=" + passenger +
                '}';
    }
}
