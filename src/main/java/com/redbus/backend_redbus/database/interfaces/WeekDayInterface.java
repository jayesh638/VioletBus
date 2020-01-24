package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.WeekDays;

public interface WeekDayInterface {
    void saveWeekDay(WeekDays weekDays);

    WeekDays findByDayName(String dayName);

    WeekDays findById(long id);

    void deleteWeekDay(WeekDays weekDays);
}
