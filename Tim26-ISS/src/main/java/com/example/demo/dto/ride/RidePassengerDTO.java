package com.example.demo.dto.ride;

import com.example.demo.model.Passenger;

public class RidePassengerDTO {
    private Integer id;
    private String email;

    public RidePassengerDTO(){};

    public RidePassengerDTO(Integer id, String email) {
        this.id = id;
        this.email = email;
    }
    public RidePassengerDTO(Passenger passenger){
        this.id = passenger.getId();
        this.email = passenger.getEmail();
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
        return "RidePassengerDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
