package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.LocationInterface;
import com.redbus.backend_redbus.database.interfaces.PointsTableInterface;
import com.redbus.backend_redbus.model.Location;
import com.redbus.backend_redbus.model.PointsTable;
import com.redbus.backend_redbus.request.and.responses.RequestRouteAdd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PointsTableHelper {
    private Logger logger= LoggerFactory.getLogger(PointsTableHelper.class);
    @Autowired
    private LocationInterface locationInterface;
    @Autowired
    private PointsTableInterface pointsTableInterface;
    void startingPointAdder(String StartingPoint, RequestRouteAdd requestRouteAdd, List<PointsTable> routePoints,List<Boolean> requestRoutedIsStopping) throws Exception {
        Location location = new Location();
        Location location2 = locationInterface.findByCityName(StartingPoint);
        if (location2 == null) {
            logger.warn("Not in the database so creating a new city");
            location.setCityName(StartingPoint);
            int routeSize = requestRouteAdd.getRouteBoardingPoints().size();
            for (int i = 0; i < routeSize; i++) {
                PointsTable pointsTable = new PointsTable();
                pointsTable.setPointName(requestRouteAdd.getRouteBoardingPoints().get(i));
                pointsTable.setLandMark(requestRouteAdd.getRouteBoardingLandmarks().get(i));
                pointsTable.setBoardingTime(requestRouteAdd.getBoardingPointsArrivalTime().get(i));
                pointsTable.setDepartureTime(requestRouteAdd.getBoardingPointsDepartureTime().get(i));
                pointsTable.setPointType("Boarding");
                pointsTable.setStoppingPoint(requestRoutedIsStopping.get(i));

                logger.info("Saving Points Table");
                try {
                    pointsTableInterface.savePoints(pointsTable);
                } catch (Exception e) {
                    logger.error("Error while saving the pointsTable");
                    throw new Exception(e);
                }
                location.getBoardingPoints().add(pointsTable);
                routePoints.add(pointsTable);
            }
            logger.info("Saving the location now");
            try {
                locationInterface.saveLocation(location);
            } catch (Exception e) {
                logger.error("Error while saving the location", e);
                throw new Exception(e);
            }
        } else {
            logger.info("Inside else");
            int routeSize = requestRouteAdd.getRouteBoardingPoints().size();
            for (int i = 0; i < routeSize; i++) {
                PointsTable pointsTable = new PointsTable();
                pointsTable.setPointName(requestRouteAdd.getRouteBoardingPoints().get(i));
                pointsTable.setLandMark(requestRouteAdd.getRouteBoardingLandmarks().get(i));
                pointsTable.setBoardingTime(requestRouteAdd.getBoardingPointsArrivalTime().get(i));
                pointsTable.setDepartureTime(requestRouteAdd.getBoardingPointsDepartureTime().get(i));
                pointsTable.setPointType("Boarding");
                pointsTable.setStoppingPoint(requestRoutedIsStopping.get(i));
                logger.info("Saving the point table");
                try {
                    pointsTableInterface.savePoints(pointsTable);
                } catch (Exception e) {
                    logger.error("Error while saving the pointTable in the database", e);
                    throw new Exception(e);
                }
                location2.getBoardingPoints().add(pointsTable);
                routePoints.add(pointsTable);
            }
            logger.info("Now Saving the location Outside else");
            try {
                locationInterface.saveLocation(location2);
            } catch (Exception e) {
                logger.error("Error while saving the location outside else", e);
                throw new Exception(e);
            }
        }
    }
    void endPointAdder(String EndingPoint, RequestRouteAdd requestRouteAdd, List<PointsTable> routePoints,List<Boolean> requestRoutedIsStopping) throws Exception {
        System.out.println(";aldjhkadvkjsdbc");
        Location location = new Location();
        System.out.println("sdSD");
        Location locationFound = locationInterface.findByCityName(EndingPoint);
        System.out.println("zdvzvz;kln sl h ");
        if (locationFound == null) {
            System.out.println("ZDfzdf");
            System.out.println(EndingPoint);
            location.setCityName(EndingPoint);
            int routeDSize = requestRouteAdd.getRouteDroppingPoints().size();
            System.out.println(routeDSize);
            for (int i = 0; i < routeDSize; i++) {
                System.out.println("'aefaefaefae");
                PointsTable pointsTable = new PointsTable();
                pointsTable.setPointName(requestRouteAdd.getRouteDroppingPoints().get(i));
                pointsTable.setLandMark(requestRouteAdd.getRouteDroppingPointsLandmark().get(i));
                pointsTable.setBoardingTime(requestRouteAdd.getDroppingPointsArrivalTime().get(i));
                pointsTable.setDepartureTime(requestRouteAdd.getDroppingPointsDepartureTime().get(i));
                pointsTable.setStoppingPoint(requestRoutedIsStopping.get(i));
                pointsTable.setPointType("Dropping");
                System.out.println("aefaefaefaefaefaefaefaef" +
                        "");
                logger.info("Saving the pointsTable");
                try {
                    pointsTableInterface.savePoints(pointsTable);
                }catch (Exception e)
                {
                    logger.error("Error while saving the pointTable in the database");
                    throw new Exception(e);
                }
                location.getBoardingPoints().add(pointsTable);
                routePoints.add(pointsTable);
            }
            try {
                locationInterface.saveLocation(location);
            }catch (Exception e)
            {
                logger.error("Error while saving the data in location",e);
                throw new Exception(e);
            }
        }else {
            logger.info("Inside else ");
            locationFound.setCityName(EndingPoint);
            int routeDSize = requestRouteAdd.getRouteDroppingPoints().size();
            for (int i = 0; i < routeDSize; i++) {
                PointsTable pointsTable = new PointsTable();
                pointsTable.setPointName(requestRouteAdd.getRouteDroppingPoints().get(i));
                pointsTable.setLandMark(requestRouteAdd.getRouteDroppingPointsLandmark().get(i));
                pointsTable.setBoardingTime(requestRouteAdd.getDroppingPointsArrivalTime().get(i));
                pointsTable.setDepartureTime(requestRouteAdd.getDroppingPointsDepartureTime().get(i));
                pointsTable.setStoppingPoint(requestRoutedIsStopping.get(i));
                pointsTable.setPointType("Dropping");
                logger.info("Now saving the data") ;
                try {
                    pointsTableInterface.savePoints(pointsTable);
                }catch (Exception e)
                {
                    logger.error("Error while saving the pointsTable ",e);
                    throw new Exception(e);
                }
                locationFound.getBoardingPoints().add(pointsTable);
                routePoints.add(pointsTable);
            }
            try {
                locationInterface.saveLocation(locationFound);
            }catch (Exception e)
            {
                logger.error("Error while saving the location");
                throw new Exception(e);
            }
        }

    }
}
