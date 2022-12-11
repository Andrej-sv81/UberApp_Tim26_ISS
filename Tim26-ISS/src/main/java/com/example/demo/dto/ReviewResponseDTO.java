package com.example.demo.dto;

public class ReviewResponseDTO {
    private int id;
    private int rating;
    private String comment;
    private PassengerRideOverDTO passenger;

    public ReviewResponseDTO() {
        this.passenger = new PassengerRideOverDTO();
    }

    public ReviewResponseDTO(int id, int rating, String comment, PassengerRideOverDTO passenger) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.passenger = passenger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
