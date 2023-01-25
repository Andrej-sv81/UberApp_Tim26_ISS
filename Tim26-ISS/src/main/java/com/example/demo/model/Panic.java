package com.example.demo.model;

import javax.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
public class Panic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "panic_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Ride ride;

    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(name="panic_message", nullable = false)
    private String panicMessage;

    public Panic(){
        super();
    }
    public Panic(User user, Ride ride, Date time, String panicMessage) {
        this.user = user;
        this.ride = ride;
        this.time = time;
        this.panicMessage = panicMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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
