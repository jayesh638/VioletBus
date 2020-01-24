package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.WeekDayInterface;
import com.redbus.backend_redbus.model.Bus;
import com.redbus.backend_redbus.model.WeekDays;
import com.redbus.backend_redbus.repository.WeekDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeekDayService implements WeekDayInterface {
    @Autowired
    private WeekDayRepository weekDayRepository;

    @Override
    public void saveWeekDay(WeekDays weekDays) {
        weekDayRepository.save(weekDays);
    }

    @Override
    public WeekDays findByDayName(String dayName) {
        return weekDayRepository.findByDayName(dayName);
    }

    @Override
    public WeekDays findById(long id) {
        return weekDayRepository.findById(id).get();
    }

    @Override
    public void deleteWeekDay(WeekDays weekDays) {
        weekDayRepository.delete(weekDays);
    }
}
