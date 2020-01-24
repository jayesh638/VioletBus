package com.redbus.backend_redbus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SeatFare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String seatName;
    private int seatPrice;
    private String seatType;
    private String isWindowSeat;
    private String isBooked;
    private String bookingGender;
}
