package com.example.demo.dto;

public class ReviewRequestDTO {
    private int rating;
    private String comment;

    public ReviewRequestDTO() {
    }

    public ReviewRequestDTO(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ReviewRequestDTO{" +
                "rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
