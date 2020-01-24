package com.redbus.backend_redbus.request.and.responses;

import com.redbus.backend_redbus.model.RouteTable;
import com.redbus.backend_redbus.model.SeatFare;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class ResponseBody {
    private  long busId;
    private float busRating;
    private String dateOfDeparture;
    private String busName;
    private String busNumber;
    private String busDriver;
    private String busType;
    private String duration;
    private String startingPrice;
    private boolean isSleeperAvailable;
    private List<SeatFare> seatFareList= new ArrayList<>();
    private RouteTable routeTable;
    private String departure;
    private String arrival;
    private int totalSeats;

}
