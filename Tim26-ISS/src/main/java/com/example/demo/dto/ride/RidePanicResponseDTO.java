package com.example.demo.dto.ride;

import com.example.demo.dto.user.UserResponseDTO;

import java.time.LocalDateTime;

public class RidePanicResponseDTO {
   private int id;
   private UserResponseDTO user;
   private RideDTO ride;
   private LocalDateTime time;
   private String reason;

    public RidePanicResponseDTO() {
        this.time = LocalDateTime.now();
    }

    public RidePanicResponseDTO(int id, UserResponseDTO user, RideDTO ride, LocalDateTime time, String reason) {
        this.id = id;
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserResponseDTO getUser() {
        return user;
    }

    public void setUser(UserResponseDTO user) {
        this.user = user;
    }

    public RideDTO getRide() {
        return ride;
    }

    public void setRide(RideDTO ride) {
        this.ride = ride;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime tine) {
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
        return "RidePanicResponseDTO{" +
                "id=" + id +
                ", user=" + user +
                ", ride=" + ride +
                ", tine=" + time +
                ", reason='" + reason + '\'' +
                '}';
    }
}
