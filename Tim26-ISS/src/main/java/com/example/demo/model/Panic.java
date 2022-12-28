package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
public class Panic {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "panic_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Ride ride;

    @Column(name = "time", nullable = false)
    private Time time;

    @Column(name="panic_message", nullable = false)
    private String panicMessage;

    public Panic(){
        super();
    }
    public Panic(User user, Ride ride, Time time, String panicMessage) {
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.panicMessage = panicMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getPanicMessage() {
        return panicMessage;
    }

    public void setPanicMessage(String panicMessage) {
        this.panicMessage = panicMessage;
    }

    @Override
    public String toString() {
        return "Panic{" +
                "id=" + id +
                ", user=" + user +
                ", ride=" + ride +
                ", time='" + time + '\'' +
                ", panicMessage='" + panicMessage + '\'' +
                '}';
    }
}
