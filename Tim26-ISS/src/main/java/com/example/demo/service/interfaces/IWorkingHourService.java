package com.example.demo.service.interfaces;

import com.example.demo.model.Driver;
import com.example.demo.model.WorkingHour;

import java.util.List;

public interface IWorkingHourService {

    public List<WorkingHour> getWorkingHoursByDriver(Integer id);
    public List<WorkingHour> getWorkingHoursByDriver(Driver driver);
    public boolean validateDriverWorkingHours(Integer id);
    public List<WorkingHour> getWorkingHoursForValidation(Integer id);  // get working hours in last 24H
}
