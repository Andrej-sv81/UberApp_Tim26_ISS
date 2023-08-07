package com.example.demo.service.interfaces;

import com.example.demo.model.Driver;
import com.example.demo.model.WorkingHour;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface IWorkingHourService {

    public List<WorkingHour> getWorkingHoursByDriver(Integer id);
    public List<WorkingHour> getWorkingHoursByDriver(Driver driver);
    Duration getTotalHoursWorkedInLastDay(Integer id);
}
