package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.*;
import com.redbus.backend_redbus.model.*;
import com.redbus.backend_redbus.request.and.responses.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BusServiceHelper {

    private Logger logger = LoggerFactory.getLogger(BusServiceHelper.class);
    @Autowired
    private SaveSeatHelper saveSeatHelper;
    @Autowired
    private BusDriverInterface busDriverInterface;
    @Autowired
    private RouteTableInterface routeTableInterface;
    @Autowired
    private BusInterface busInterface;
    @Autowired
    private BusTypeInterface busTypeInterface;
    @Autowired
    private PointsTableHelper pointsTableHelper;
    @Autowired
    private RouteTableHelper routeTableHelper;
    @Autowired
    private WeekDayHelper weekDayHelper;
    @Autowired
    private ValidityChecker validityChecker;
    @Autowired
    private RouteServiceHelper routeServiceHelper;
    @Autowired
    private BusRatingInterface busRatingInterface;
    public String editBus(EditBusRequest editBusRequest) {
        logger.info("Inside edit function and checking validity");
        if (validityChecker.checkEditBusValidity(editBusRequest)) {
            logger.warn("Incorrect data format received");
            return "Incorrect Data Format";
        }
        Optional<Bus> bus1 = busInterface.findByBusId(Long.parseLong(editBusRequest.getBusId()));
        if (!bus1.isPresent()) {
            logger.warn("Bus is not in the database");
            return "Bus do not exist in the database";
        }
        logger.info("Bus found in the database now updating bus details");
        Bus bus = bus1.get();
        bus.setName(editBusRequest.getBusName());
        try {
            BusDriver busDriver1 = busDriverInterface.findByNumber(editBusRequest.getBusDriverNumber());
            if (busDriver1 != null) {
                logger.warn("Bus driver is already registered with another bus");
                return "Bus driver number is already registered with another Bus";
            }
        }catch (Exception e)
        {
            logger.error("Error occurred while checking the driver details",e);
            return "Error occurred while saving the data";
        }
        logger.info("Deleting old bus driver from the database");
        BusDriver busDriverOld = bus.getDriver();
        try {
            busDriverInterface.deleteDriver(busDriverOld);
        }catch (Exception e)
        {
            logger.error("Error while deleting the old bus driver data" ,e);
            return "error occurred while changing the data";
        }
        BusDriver busDriver = new BusDriver();
        busDriver.setDriverPhoneNumber(editBusRequest.getBusDriverNumber());
        busDriver.setDriverName(editBusRequest.getBusDriverName());
        try {
            busDriverInterface.saveBusDriver(busDriver);
        }catch (Exception e)
        {
            logger.error("Error while saving the bus driver data",e);
            return "Error occurred while saving the bus driver";
        }
        bus.setDriver(busDriver);
        try {
            busInterface.saveBus(bus);
        }catch (Exception e)
        {
            logger.error("Error while saving the bus data",e);
            return "Error occurred while saving the bus data";
        }

        busDriver.setBusId(bus);
        try {
            busDriverInterface.saveBusDriver(busDriver);
        }catch (Exception e)
        {
            logger.error("Error while saving the bus driver data",e);
            return "Error occurred while saving the bus driver data";
        }
        return "Bus Info edited Successfully";
    }


    public String addBus(RequestBusAdd requestBusAdd) throws Exception {
        logger.info("Inside AddBus Function");
        Bus bus1 = busInterface.findByBusNumber(requestBusAdd.getBusNumber());
        if (bus1 != null) {
            return "Bus with the number " + requestBusAdd.getBusNumber() + " already Exist in the database";
        }
        if (validityChecker.checkValidity(requestBusAdd.getArrivalAndDepartureTime(), requestBusAdd.getRouteStartingAndEndpoints(), requestBusAdd.getRouteBoardingPoints(), requestBusAdd.getRouteDroppingPoints(), requestBusAdd.getRouteDroppingPointsLandmark(), requestBusAdd.getRouteAvailability(), requestBusAdd.getRouteBoardingLandmarks(), requestBusAdd.getDroppingPointsDepartureTime(), requestBusAdd.getBoardingPointsDepartureTime(), requestBusAdd.getBoardingPointsArrivalTime(), requestBusAdd.getDroppingPointsArrivalTime(), requestBusAdd.getDuration(), requestBusAdd.getBoardingRestPoint(), requestBusAdd.getDroppingPointRest()))
            return "Incorrect Format/Incomplete  data";
        Bus bus = new Bus();
        BusDriver busDriver = new BusDriver();
        BusType busType = new BusType();
        busType.setType(requestBusAdd.getBusType());
        logger.info("Saving BusType in the Database");
        try {
            busTypeInterface.saveBusType(busType);
        } catch (Exception e) {
            logger.error("Error occurred while saving the BusType", e);
            throw new Exception(e);
        }
        bus.setName(requestBusAdd.getBusName());
        bus.setBusType(busType);
        busDriver.setDriverName(requestBusAdd.getBusDriver());
        busDriver.setDriverPhoneNumber(requestBusAdd.getBusDriverNumber());
        BusRating busRating= new BusRating();
        busRating.setOverallRating(5);
        busRating.setTotalRating(1);
        busRating.setTotalRated(5);
        busRatingInterface.saveBusRating(busRating);
        logger.info("Saving Bus Driver in the Database");
        try {
            busDriverInterface.saveBusDriver(busDriver);
        } catch (Exception e) {
            logger.error("Error while saving the database", e);
            throw new Exception(e);
        }

        bus.setDriver(busDriver);
        bus.setNumber(requestBusAdd.getBusNumber());
        bus.setTotalSeat(requestBusAdd.getBusTotalSeat());
        bus.setSleeperAvailable(requestBusAdd.isSleeperAvailable());
        RouteTable routeTable = new RouteTable();
        for (String day : requestBusAdd.getRouteAvailability()) {
            try {
                List<SeatFare> seatFareList = saveSeatHelper.saveSeat(requestBusAdd.getTotalNonWindowSeatingSeats(), requestBusAdd.getNonWindowSittingSeatsPrice(), requestBusAdd.getTotalWindowSeatingSeats(), requestBusAdd.getWindowSittingSeatsPrice(), requestBusAdd.getTotalWindowSleeperSeats(), requestBusAdd.getWindowSleeperSeatsPrice(), requestBusAdd.getNonWindowSleeperSeatsPrice(), requestBusAdd.getTotalNonWindowSleeperSeats(), requestBusAdd.getBusName());
                weekDayHelper.saveWeekDay(seatFareList, routeTable, day);
            } catch (Exception e) {
                logger.error("Error while saving  in bus adding", e);
                throw new Exception(e);
            }
        }
        routeServiceHelper.setRoute(routeTable, requestBusAdd.getRouteStartingAndEndpoints(), requestBusAdd.getArrivalAndDepartureTime(), requestBusAdd.getStartingPrice(), requestBusAdd.getDuration());
        RequestRouteAdd requestRouteAdd = new RequestRouteAdd();
        requestRouteAdd.setDroppingPointsDepartureTime(requestBusAdd.getDroppingPointsDepartureTime());
        requestRouteAdd.setRouteBoardingPoints(requestBusAdd.getRouteBoardingPoints());
        requestRouteAdd.setRouteBoardingLandmarks(requestBusAdd.getRouteBoardingLandmarks());
        requestRouteAdd.setBoardingPointsArrivalTime(requestBusAdd.getBoardingPointsArrivalTime());
        requestRouteAdd.setBoardingPointsDepartureTime(requestBusAdd.getBoardingPointsDepartureTime());
        requestRouteAdd.setDroppingPointsArrivalTime(requestBusAdd.getDroppingPointsArrivalTime());
        requestRouteAdd.setRouteDroppingPoints(requestBusAdd.getRouteDroppingPoints());
        requestRouteAdd.setRouteDroppingPointsLandmark(requestBusAdd.getRouteDroppingPointsLandmark());
        requestRouteAdd.setBoardingRestPoint(requestBusAdd.getBoardingRestPoint());
        requestRouteAdd.setDroppingPointRest(requestBusAdd.getDroppingPointRest());
        List<PointsTable> routePoints = new ArrayList<>();
        logger.info("Finding if the location already exist in the database");
        pointsTableHelper.startingPointAdder(requestBusAdd.getRouteStartingAndEndpoints().get(0), requestRouteAdd, routePoints, requestRouteAdd.getBoardingRestPoint());
        logger.info("Finding if dropping point location already is in the database");
        pointsTableHelper.endPointAdder(requestBusAdd.getRouteStartingAndEndpoints().get(1), requestRouteAdd, routePoints, requestRouteAdd.getDroppingPointRest());
        bus.setBusRating(busRating);
        routeTableHelper.saveRoute(requestBusAdd.getRouteAvailability(), routeTable, routePoints, bus);
        try {
            busInterface.saveBus(bus);
        } catch (Exception e) {
            logger.error("Error while saving the bus data", e);
            throw new Exception(e);
        }
        busRating.setBus(bus);
        busRatingInterface.saveBusRating(busRating);
        routeTable.setBusID(bus);
        busDriver.setBusId(bus);
        busType.setBusTypeId(bus);
        try {
            busTypeInterface.saveBusType(busType);
        } catch (Exception e) {
            logger.error("Error while saving the busType Data", e);
            throw new Exception(e);
        }
        try {
            busDriverInterface.saveBusDriver(busDriver);
        } catch (Exception e) {
            logger.error("Error while saving the busDriver data after saving bus", e);
            throw new Exception(e);
        }
        try {
            routeTableInterface.saveRoute(routeTable);
        } catch (Exception e) {
            logger.error("Error while saving the route after saving bus", e);
            throw new Exception(e);
        }
        return "Bus added Successfully in the Database";
    }






    public List<ResponseBody> searchBus(String startingPoint, String endingPoint, String dateOfJourney) throws ParseException {
        logger.info("Inside search bus function");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.ENGLISH);
        Date myDate = sdf.parse(dateOfJourney);
        sdf.applyPattern("EEE, d MMM yyyy");
        String sMyDate = sdf.format(myDate);
        String[] weekDay = sMyDate.split(",");
        logger.info("Finding the route data by Starting and Endpoints");
        List<RouteTable> routeTableList1 = routeTableInterface.findRouteByCityNames(startingPoint, endingPoint);
        List<RouteTable> routeTableList = new ArrayList<>();
        for (RouteTable routeTable : routeTableList1) {
            String dayAvailability = routeTable.getRouteAvailability();
            String[] days = dayAvailability.split(",");
            for (String day : days) {
                System.out.println("Checking equals between: " + day.substring(0, 3) + "  " + sMyDate.substring(0, 3));
                if (day.substring(0, 3).equals(sMyDate.substring(0, 3))) {
                    routeTableList.add(routeTable);
                    logger.info("Available on the required Day");
                    break;
                }
            }
        }

        List<Bus> busList = new ArrayList<>();
        for (RouteTable routeTable : routeTableList) {
            busList.add(routeTable.getBusID());
        }
        List<ResponseBody> searchList = new ArrayList<>();
        int i = 0;
        for (Bus bus : busList) {

            logger.info("Formatting the bus data to response data");
            ResponseBody responseBody = new ResponseBody();
            responseBody.setDateOfDeparture(sMyDate);
            responseBody.setBusName(bus.getName());
            responseBody.setBusId(bus.getId());
            responseBody.setBusRating(bus.getBusRating().getOverallRating());
            responseBody.setBusNumber(bus.getNumber());
            responseBody.setBusDriver(bus.getDriver().getDriverName());
            responseBody.setBusType(bus.getBusType().getType());
            responseBody.setDuration(routeTableList.get(i).getDuration());
            int price = routeTableList.get(i).getStartingPrice();
            responseBody.setStartingPrice(String.valueOf(price));
            List<WeekDays> weekDays = routeTableList.get(i).getWeekDays();
            List<SeatFare> seatFareList = new ArrayList<>();
            for (WeekDays weekDays1 : weekDays) {
                String s = weekDays1.getDayName().substring(0, 3);
                if (s.equals(weekDay[0])) {
                    seatFareList = weekDays1.getSeatFareList();
                    logger.info("Route exist on the day searched by the user");
                }
            }
            if (seatFareList.size() == 0) {
                logger.info("Route exist but not on the day searched by the user");
                i++;
                continue;
            }
            seatFareList.sort(new SortBySeatId());
            responseBody.setSeatFareList(seatFareList);
            responseBody.setTotalSeats(Integer.parseInt(bus.getTotalSeat()));
            responseBody.setDeparture(routeTableList.get(i).getDepartureTime());
            responseBody.setArrival(routeTableList.get(i).getArrivalTime());
            responseBody.setSleeperAvailable(bus.isSleeperAvailable());
            responseBody.setRouteTable(routeTableList.get(i));
            logger.info("Adding the response body to list");
            searchList.add(responseBody);
            i++;
        }
        logger.info("Returning searchList now");
        return searchList;
    }
}
