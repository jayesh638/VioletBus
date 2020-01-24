package com.redbus.backend_redbus.request.and.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class RequestRouteAdd {
    private String busId;
    private List<String> routeAvailability;
    private List<String> arrivalAndDepartureTime;
    private List<String> routeStartingAndEndpoints;
    private List<String> routeBoardingPoints;
    private List<String> routeBoardingLandmarks;
    private List<String> routeDroppingPoints;
    private List<String> routeDroppingPointsLandmark;
    private List<String> droppingPointsArrivalTime;
    private List<String> droppingPointsDepartureTime;
    private List<String> boardingPointsArrivalTime;
    private List<String> boardingPointsDepartureTime;
    private List<Boolean> boardingRestPoint;
    private List<Boolean> droppingPointRest;
     private String duration;
    private boolean sleeperAvailable;
    private int totalWindowSleeperSeats;
    private int totalNonWindowSleeperSeats;
    private int totalWindowSeatingSeats;
    private int totalNonWindowSeatingSeats;
    private int startingPrice;
    private int windowSleeperSeatsPrice;
    private int windowSittingSeatsPrice;
    private int nonWindowSleeperSeatsPrice;
    private int nonWindowSittingSeatsPrice;
}
