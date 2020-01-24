package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.BusRatingInterface;
import com.redbus.backend_redbus.model.BusRating;
import com.redbus.backend_redbus.repository.BusRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BusRatingService implements BusRatingInterface {
    @Autowired
    private BusRatingRepository busRatingRepository;

    @Override
    public void saveBusRating(BusRating busRating) {
        busRatingRepository.save(busRating);
    }

    @Override
    public Optional<BusRating> findMyBusRating(long busId) {
        return busRatingRepository.findById(busId);
    }
}
