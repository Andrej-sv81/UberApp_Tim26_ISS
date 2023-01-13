package com.example.demo.dto.driver;

public class DriverRideOverDTO {
    private Integer id;
    private String email;

    public DriverRideOverDTO() {
    }

    public DriverRideOverDTO(Integer id, String email) {
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
        return "DriverRideOverDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
