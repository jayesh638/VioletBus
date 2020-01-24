package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.SeatFare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatFareRepository extends JpaRepository<SeatFare,Integer> {

    List<SeatFare> findBySeatType(String seatType);
    SeatFare findBySeatName(String seatName);
}
