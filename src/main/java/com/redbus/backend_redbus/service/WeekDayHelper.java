package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.WeekDayInterface;
import com.redbus.backend_redbus.model.RouteTable;
import com.redbus.backend_redbus.model.SeatFare;
import com.redbus.backend_redbus.model.WeekDays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeekDayHelper {
    private Logger logger= LoggerFactory.getLogger(WeekDayHelper.class);
    @Autowired
    private WeekDayInterface weekDayInterface;
    void saveWeekDay(List<SeatFare> seatFareList, RouteTable routeTable,String day) throws Exception {
        logger.info("Got the seat List from SaveSeat Helper function");
        WeekDays weekDays = new WeekDays();
        weekDays.setDayName(day);
        weekDays.setSeatFareList(seatFareList);
        logger.info("saving weekdays");
        try {
            weekDayInterface.saveWeekDay(weekDays);
        } catch (Exception e) {
            logger.error("Error while saving the weekday");
            throw new Exception(e);
        }
        routeTable.getWeekDays().add(weekDays);
    }
}
