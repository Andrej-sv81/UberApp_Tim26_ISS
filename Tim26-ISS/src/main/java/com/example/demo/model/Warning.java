package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Warning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warning_id", nullable = false)
    private Integer id;
    @Column(name="message", nullable = false)
    private String message;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Warning(){
        super();
    }
    public Warning(String message, User user) {
        this.message = message;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Warning{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", user=" + user +
                '}';
    }
}
