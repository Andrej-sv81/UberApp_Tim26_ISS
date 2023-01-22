package com.example.demo.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class ReviewRequestDTO {
    @Min(0)
    @Max(5)
    private int rating;
    @Length(max = 255)
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
