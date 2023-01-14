package com.example.demo.model;

import javax.persistence.*;

import java.sql.Time;

@Entity
public class RejectionMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "rmessage_id", nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Ride ride;
    @Column(name="rejection_reason", nullable = false)
    private String rejectionReason;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;
    @Column(name="time_of_rejection", nullable = false)
    private Time timeOfRejection;

    public RejectionMessage(){
        super();
    }
    public RejectionMessage(Ride ride, String rejectionReason, User user, Time timeOfRejection) {
        this.ride = ride;
        this.rejectionReason = rejectionReason;
        this.user = user;
        this.timeOfRejection = timeOfRejection;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Time getTimeOfRejection() {
        return timeOfRejection;
    }

    public void setTimeOfRejection(Time timeOfRejection) {
        this.timeOfRejection = timeOfRejection;
    }

    @Override
    public String toString() {
        return "RejectionMessage{" +
                "id=" + id +
                ", ride=" + ride +
                ", rejectionReason='" + rejectionReason + '\'' +
                ", user=" + user +
                ", timeOfRejection=" + timeOfRejection +
                '}';
    }
}
