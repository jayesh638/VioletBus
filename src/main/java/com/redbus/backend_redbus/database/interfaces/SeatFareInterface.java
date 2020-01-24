package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.SeatFare;

import java.util.List;
import java.util.Optional;

public interface SeatFareInterface {
    void saveSeatFare(SeatFare seatFare);

    List<SeatFare> findBySeatType(String seatType);

    SeatFare findBySeatName(String seatName);

    Optional<SeatFare> findById(int id);
}
