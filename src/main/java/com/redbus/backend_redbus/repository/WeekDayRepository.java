package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.WeekDays;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekDayRepository extends JpaRepository<WeekDays,Long> {
    WeekDays findByDayName(String dayName);
}
