package com.example.demo.dto.driver;

import com.example.demo.model.WorkingHour;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DriverWorkingHourResponse {
    private Integer id;
    private String start;
    private String end;

    public DriverWorkingHourResponse() {
    }

    public DriverWorkingHourResponse(Integer id, String start, String end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public DriverWorkingHourResponse(WorkingHour created){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.id = created.getId();
        this.start = dateFormat.format(created.getStartTime());      //TODO formatiraj lepo ovaj string
        this.end = dateFormat.format(created.getEndTime());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "DriverWorkingHourResponse{" +
                "id=" + id +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}
