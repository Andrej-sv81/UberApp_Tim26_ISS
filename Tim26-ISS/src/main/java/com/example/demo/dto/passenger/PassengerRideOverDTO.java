package com.example.demo.dto.passenger;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PassengerRideOverDTO {
    @NotNull
    @Min(1)
    private Integer id;
    @Email
    private String email;

    public PassengerRideOverDTO() {
    }

    public PassengerRideOverDTO(Integer id, String email) {
        this.id = id;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "PassengerRideOverDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
