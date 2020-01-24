package com.redbus.backend_redbus.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Ticket {
    @Id
    String id;
    @ManyToOne
    @JsonIgnore
    private UserTable user;
    private String busName;
    private String busId;
    private String routeId;
    /* private String driverName;
     private String driverPhone;
     private String busType;
     private String seatType;*/
    private String nameOnTicket;
    private String genderOnTicket;
    private String ageOnTicket;
    private String emailOnTicket;
    private String phoneOnTicket;
    private String fare;
//    private String seatName;

    private String status;

    private String seatNumber;
    private String dateOfBooking;
    private String dateOfJourney;
    private String dayOfBooking;
    private String dayOfJourney;
    private String boardingPoint;
    private String droppingPoint;
    private String boardingTime;
    private String droppingTime;
}
