package com.example.demo.service;

import com.example.demo.model.Driver;
import com.example.demo.model.WorkingHour;
import com.example.demo.repository.WorkingHourRepository;
import com.example.demo.service.interfaces.IWorkingHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
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
    public Duration getTotalHoursWorkedInLastDay(Integer id) {
        //TODO ODRADI GET TOTAL HOURS WORKED IN LAST DAY
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        //List<WorkingHour> workingHours = workingHourRepository.findAllByDriverIdAndEndTimeAfter(id, yesterday); //TODO
        List<WorkingHour> workingHours = null;
        if(workingHours.size() == 0) return Duration.ZERO;
        Duration totalDurationWorked = Duration.ZERO;
        for(WorkingHour workingHour: workingHours){
            if(workingHour.getStartTime().equals(workingHour.getEndTime())){
                totalDurationWorked = totalDurationWorked.plus(Duration.between((Temporal) workingHour.getStartTime(), LocalDateTime.now()));
                continue;
            }
            if(workingHour.getStartTime().after(Date.from(yesterday.atZone(ZoneId.of("Europe/Paris")).toInstant()))){
                totalDurationWorked = totalDurationWorked.plus(Duration.between((Temporal) workingHour.getStartTime(), (Temporal) workingHour.getEndTime()));
            }else{
                totalDurationWorked = totalDurationWorked.plus(Duration.between((Temporal) yesterday, (Temporal) workingHour.getEndTime()));
            }
        }
        return totalDurationWorked;
    }
}
