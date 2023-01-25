package com.example.demo.model;
import javax.persistence.*;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class WorkingHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name="start_time", nullable = false)
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name="end_time", nullable = false)
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;
    @OneToOne
    private Driver driver;


    public WorkingHour(){
        super();
    }
    public WorkingHour(Date startTime, Date endTime, Driver driver) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.driver = driver;
    }

    public  WorkingHour(Driver driver, String start) throws ParseException {
        this.driver = driver;
        this.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start));
        this.setEndTime(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(start));
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.util.Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
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
