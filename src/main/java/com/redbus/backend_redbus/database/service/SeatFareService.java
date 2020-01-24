package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.SeatFareInterface;
import com.redbus.backend_redbus.model.SeatFare;
import com.redbus.backend_redbus.repository.SeatFareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SeatFareService implements SeatFareInterface {
    @Autowired
    private SeatFareRepository seatFareRepository;

    @Override
    public void saveSeatFare(SeatFare seatFare) {
        seatFareRepository.save(seatFare);
    }

    @Override
    public List<SeatFare> findBySeatType(String seatType) {
        return seatFareRepository.findBySeatType(seatType);
    }

    @Override
    public SeatFare findBySeatName(String seatName) {
        return seatFareRepository.findBySeatName(seatName);
    }

    @Override
    public Optional<SeatFare> findById(int id) {
        return seatFareRepository.findById(id);
    }
}
