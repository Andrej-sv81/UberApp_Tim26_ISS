package com.example.demo.service;

import com.example.demo.model.Driver;
import com.example.demo.model.WorkingHour;
import com.example.demo.repository.WorkingHourRepository;
import com.example.demo.service.interfaces.IWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WorkingHourService implements IWorkingHourService {

    @Autowired
    private WorkingHourRepository workingHourRepository;


    @Override
    public List<WorkingHour> getWorkingHoursByDriver(Integer id) {
        return workingHourRepository.getWorkingHourByDriver_Id(id);
    }

    @Override
    public List<WorkingHour> getWorkingHoursByDriver(Driver driver) {
        return workingHourRepository.getWorkingHourByDriver(driver);
    }

    @Override
    public boolean validateDriverWorkingHours(Integer id) {
        List<WorkingHour> workingHours = getWorkingHoursForValidation(id);
        long totalWorkingMinutes = 0L;
        if (workingHours.isEmpty())
            return true; // driver is valid and can accept ride
        for (WorkingHour wh : workingHours){
            totalWorkingMinutes += ChronoUnit.MINUTES.between((Temporal) wh.getStartTime(), (Temporal) wh.getEndTime());
        }
        if (totalWorkingMinutes>8L*60L)
            return false;
        return true;
    }

    @Override
    public List<WorkingHour> getWorkingHoursForValidation(Integer id) {
        List<WorkingHour> allWorkingHours = workingHourRepository.getWorkingHourByDriver_Id(id);
        Date dayBefore = new Date();
        //  TODO ODUZMI 24H!!
        if (allWorkingHours.isEmpty())
            return allWorkingHours;
        for (WorkingHour wh:allWorkingHours){
            if (wh.getEndTime().before(dayBefore)) {
                allWorkingHours.remove(wh);
            } else if (wh.getStartTime().before(dayBefore)) {
                wh.setStartTime(dayBefore);
            } else if (wh.getStartTime().equals(wh.getEndTime())) {
                wh.setEndTime(new Date());  // znaci naisao je na trenutni working hour vozac je idalje aktivan
            }
        }
        return allWorkingHours;
    }
}
