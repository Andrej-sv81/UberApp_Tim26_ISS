package com.example.demo.dto;

public class PanicResponseDTO {

    private Integer id;
    private UserResponseDTO user;
    private RideResponseDTO ride;
    private String time;
    private String reason;

    public PanicResponseDTO(){};
    public PanicResponseDTO(Integer id, UserResponseDTO user, RideResponseDTO ride, String time, String reason) {
        this.id = id;
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public RideResponseDTO getRide() {
        return ride;
    }

    public void setRide(RideResponseDTO ride) {
        this.ride = ride;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "PanicResponseDTO{" +
                "id=" + id +
                ", user=" + user +
                ", ride=" + ride +
                ", time='" + time + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
