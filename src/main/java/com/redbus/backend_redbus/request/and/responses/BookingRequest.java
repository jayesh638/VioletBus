package com.redbus.backend_redbus.request.and.responses;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class BookingRequest {
    private String busName;
    private String userId;
    private String busId;
    private String routeId;
    private String emailOnTicket;
    private String phoneOnTicket;
    private String fare;
    private String dateOfJourney;
    private String dateOfBooking;
    private String dayOfJourney;
    private String dayOfBooking;
    private String paymentMethod;
    private String boardingPoint;
    private String droppingPoint;
    private String boardingTime;
    private String droppingTime;
    @JsonProperty("passengers")
    private List<Passenger> passengers;

    private String currency;
    private String mid;
    private String orderid;
    private String respcode;
    private String respmsg;
    private String status;
    private String txnamount;
}
