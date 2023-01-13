package com.example.demo.dto.user;

import com.example.demo.model.Message;

public class UserMessageResponseDTO {
    private Integer id;
    private String timeOfSending;
    private Integer senderId;
    private Integer receiverId;
    private String message;
    private String type;
    private Integer rideId;

    public UserMessageResponseDTO(){};

    public UserMessageResponseDTO(Message message) {
        this.id = message.getId();
        this.timeOfSending = message.getSentTime().toString();
        this.senderId = message.getSender().getId();
        this.receiverId = message.getReceiver().getId();
        this.message = message.getMessage();
        this.type = message.getMessageType().toString();
        this.rideId = message.getRideId();
    }
    public UserMessageResponseDTO(Integer id, String timeOfSending, Integer senderId, Integer receiverId, String message, String type, Integer rideId) {
        this.id = id;
        this.timeOfSending = timeOfSending;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.type = type;
        this.rideId = rideId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeOfSending() {
        return timeOfSending;
    }

    public void setTimeOfSending(String timeOfSending) {
        this.timeOfSending = timeOfSending;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
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
        return "UserMessageResponseDTO{" +
                "id=" + id +
                ", timeOfSending='" + timeOfSending + '\'' +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", rideId=" + rideId +
                '}';
    }
}
