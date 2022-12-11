package com.example.demo.model;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WorkingHour {

    private Integer id;
    private LocalDateTime start;
    private LocalDateTime end;


    public WorkingHour() {
    }

    public WorkingHour(Integer id, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public WorkingHour(Integer id, String start, String end) {
        this.id = id;
        this.start = LocalDateTime.parse(start, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.end = LocalDateTime.parse(end,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    

    @Override
    public String toString() {
        return "WorkingHour{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
