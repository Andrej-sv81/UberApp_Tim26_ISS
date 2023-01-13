package com.example.demo.model;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
public class WorkingHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name="start_time", nullable = false)
    private Time startTime;
    @Column(name="end_time", nullable = false)
    private Time endTime;
    @OneToOne
    private Driver driver;

    public WorkingHour(){
        super();
    }
    public WorkingHour(Time startTime, Time endTime, Driver driver) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.driver = driver;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return "WorkingHour{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", driver=" + driver +
                '}';
    }
}
