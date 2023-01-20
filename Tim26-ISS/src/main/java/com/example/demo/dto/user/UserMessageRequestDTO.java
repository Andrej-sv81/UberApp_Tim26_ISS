package com.example.demo.dto.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserMessageRequestDTO {
    @NotNull
    private Integer receiverId;
    @Length(min=1, max=255)
    private String message;
    @Pattern(regexp="^(SUPPORT|RIDE|PANIC)$")
    private String type;
    @NotNull
    private Integer rideId;

    public UserMessageRequestDTO(){};
    public UserMessageRequestDTO(Integer receiverId, String message, String type, Integer rideId) {
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRideId() {
        return rideId;
    }

    public void setRideId(Integer rideId) {
        this.rideId = rideId;
    }

    @Override
    public String toString() {
        return "UserMessageRequestDTO{" +
                "receiverId=" + receiverId +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", rideId=" + rideId +
                '}';
    }
}
