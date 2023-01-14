package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "review_id", nullable = false)
    private Long id;

    @Column(name="score", nullable = false)
    private int score;
    @Column(name="comment", nullable = true)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviews")
    private Ride ride;

    @OneToOne(fetch = FetchType.LAZY)
    private Passenger passenger;

    public Review(){
        super();
    }
    public Review(int score, String comment, Ride ride, Passenger passenger) {
        this.score = score;
        this.comment = comment;
        this.ride = ride;
        this.passenger = passenger;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                ", ride=" + ride +
                ", passenger=" + passenger +
                '}';
    }
}
