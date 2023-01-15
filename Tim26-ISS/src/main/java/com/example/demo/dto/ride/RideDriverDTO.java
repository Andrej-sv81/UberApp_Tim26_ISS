package com.example.demo.dto.ride;

public class RideDriverDTO {
    private Integer id;
    private String email;

    public RideDriverDTO(){};
    public RideDriverDTO(Integer id, String email) {
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
        return "RideDriverDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
