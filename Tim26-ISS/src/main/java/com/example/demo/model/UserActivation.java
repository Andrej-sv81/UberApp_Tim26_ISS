package com.example.demo.model;

import javax.persistence.*;

import java.sql.Date;

@Entity
public class UserActivation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_id", nullable = false)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(name="date_of_creation", nullable = false)
    private Date date;
    @Column(name="lifetime", nullable = false)
    private Date lifeTime;

    public UserActivation(){
        super();
    }
    public UserActivation(User user, Date date, Date lifeTime) {
        this.user = user;
        this.date = date;
        this.lifeTime = lifeTime;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Date lifeTime) {
        this.lifeTime = lifeTime;
    }

    @Override
    public String toString() {
        return "UserActivation{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", lifeTime=" + lifeTime +
                '}';
    }
}
