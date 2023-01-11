package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "message_id", nullable = false)
    private Long id;

    @OneToOne
    private User sender;
    @OneToOne
    private User receiver;
    @Column(name="message", nullable = false)
    private String message;
    @Column(name="time_sent", nullable = false)
    private Time sentTime;
    @Enumerated(EnumType.ORDINAL)
    private MessageType messageType;
    @Column(name="ride_id_for_ride_message", nullable = true)
    private Long rideId;

    public Message(){
        super();
    }
    public Message(User sender, User receiver, String message, Time sentTime, MessageType messageType, Long rideId) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.sentTime = sentTime;
        this.messageType = messageType;
        this.rideId = rideId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Time getSentTime() {
        return sentTime;
    }

    public void setSentTime(Time sentTime) {
        this.sentTime = sentTime;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", message='" + message + '\'' +
                ", sentTime=" + sentTime +
                ", messageType=" + messageType +
                ", rideId=" + rideId +
                '}';
    }
}
