package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Warning {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "warning_id", nullable = false)
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
