package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.BusInterface;
import com.redbus.backend_redbus.database.interfaces.RouteTableInterface;
import com.redbus.backend_redbus.model.*;
import com.redbus.backend_redbus.request.and.responses.RequestRouteAdd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class RouteServiceHelper {
    private Logger logger = LoggerFactory.getLogger(RouteServiceHelper.class);
    @Autowired
    private ValidityChecker validityChecker;
    @Autowired
    private BusInterface busInterface;
    @Autowired
    private WeekDayHelper weekDayHelper;
    @Autowired
    private PointsTableHelper pointsTableHelper;
    @Autowired
    private RouteTableHelper routeTableHelper;
    @Autowired
    private RouteTableInterface routeTableInterface;
    @Autowired
    private SaveSeatHelper saveSeatHelper;

    public String addRoute(RequestRouteAdd requestRouteAdd) throws Exception {
        logger.info("Inside add Route function");
        Optional<Bus> bus = busInterface.findByBusId(Long.parseLong(requestRouteAdd.getBusId()));
        if (!bus.isPresent()) {
            return "Bus with this id is not in the database";
        }
        if (validityChecker.checkValidity(requestRouteAdd.getArrivalAndDepartureTime(), requestRouteAdd.getRouteStartingAndEndpoints(), requestRouteAdd.getRouteBoardingPoints(), requestRouteAdd.getRouteDroppingPoints(), requestRouteAdd.getRouteDroppingPointsLandmark(), requestRouteAdd.getRouteAvailability(), requestRouteAdd.getRouteBoardingLandmarks(), requestRouteAdd.getDroppingPointsDepartureTime(), requestRouteAdd.getBoardingPointsDepartureTime(), requestRouteAdd.getBoardingPointsArrivalTime(), requestRouteAdd.getDroppingPointsArrivalTime(), requestRouteAdd.getDuration(), requestRouteAdd.getBoardingRestPoint(), requestRouteAdd.getDroppingPointRest()))
            return "Incorrect Format/Incomplete  data";


        Bus bus1 = bus.get();
        RouteTable routeTable = new RouteTable();
        List<String> days = requestRouteAdd.getRouteAvailability();
        List<String> route = new ArrayList<>();
        List<RouteTable> oldRoute = bus1.getRouteTable();
        List<WeekDays> weekDaysList = new ArrayList<>();
        for (RouteTable routeTable1 : oldRoute) {
            weekDaysList.addAll(routeTable1.getWeekDays());
        }
        for (WeekDays weekDays : weekDaysList) {
            route.add(weekDays.getDayName());
        }
        System.out.println("now displaying");
        for (String s : route)
            System.out.println(s);
        for (String s : days)
            System.out.println(s);
        System.out.println("display ended");
        route.retainAll(days);
        if (route.size() > 0) {
            return "Bus is already on different route for the day";
        }
        for (String day : requestRouteAdd.getRouteAvailability()) {

            try {
                List<SeatFare> seatFareList = saveSeatHelper.saveSeat(requestRouteAdd.getTotalNonWindowSeatingSeats(), requestRouteAdd.getNonWindowSittingSeatsPrice(), requestRouteAdd.getTotalWindowSeatingSeats(), requestRouteAdd.getWindowSittingSeatsPrice(), requestRouteAdd.getTotalWindowSleeperSeats(), requestRouteAdd.getWindowSleeperSeatsPrice(), requestRouteAdd.getNonWindowSleeperSeatsPrice(), requestRouteAdd.getTotalNonWindowSleeperSeats(), bus1.getName());
                weekDayHelper.saveWeekDay(seatFareList, routeTable, day);
            } catch (Exception e) {
                logger.error("Error while saving  in bus adding", e);
                throw new Exception(e);
            }
        }
        setRoute(routeTable, requestRouteAdd.getRouteStartingAndEndpoints(), requestRouteAdd.getArrivalAndDepartureTime(), requestRouteAdd.getStartingPrice(), requestRouteAdd.getDuration());
        List<PointsTable> routePoints=new ArrayList<>();
        logger.info("Finding if the Starting location already exist in the database");
        pointsTableHelper.startingPointAdder(requestRouteAdd.getRouteStartingAndEndpoints().get(0), requestRouteAdd, routePoints, requestRouteAdd.getBoardingRestPoint());
        logger.info("Finding if the Ending location already exist in the database");
        pointsTableHelper.endPointAdder(requestRouteAdd.getRouteStartingAndEndpoints().get(1), requestRouteAdd, routePoints, requestRouteAdd.getDroppingPointRest());
        routeTableHelper.saveRoute(requestRouteAdd.getRouteAvailability(), routeTable, routePoints, bus1);
        busInterface.saveBus(bus1);
        routeTable.setBusID(bus1);
        routeTableInterface.saveRoute(routeTable);
        return "Bus Route has been added successfully";
    }
    void setRoute(RouteTable routeTable, List<String> routeStartingAndEndpoints, List<String> arrivalAndDepartureTime, int startingPrice, String duration) {
        routeTable.setStartingPoint(routeStartingAndEndpoints.get(0));
        routeTable.setEndingPoint(routeStartingAndEndpoints.get(1));
        routeTable.setArrivalTime(arrivalAndDepartureTime.get(0));
        routeTable.setDepartureTime(arrivalAndDepartureTime.get(1));
        routeTable.setStartingPrice(startingPrice);
        routeTable.setRouteName(routeStartingAndEndpoints.get(0) + " to " + routeStartingAndEndpoints.get(1));
        routeTable.setDuration(duration);
    }


}
