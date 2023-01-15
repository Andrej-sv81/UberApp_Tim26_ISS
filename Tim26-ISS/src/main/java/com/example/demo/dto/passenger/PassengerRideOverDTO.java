package com.example.demo.dto.passenger;

public class PassengerRideOverDTO {
    private Integer id;
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
