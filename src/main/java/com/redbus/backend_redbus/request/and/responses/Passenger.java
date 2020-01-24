package com.redbus.backend_redbus.request.and.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Passenger {
    String passengerName;
    String passengerGender;
    String passengerAge;
    String passengerSeatId;
    String ticketId;
}
