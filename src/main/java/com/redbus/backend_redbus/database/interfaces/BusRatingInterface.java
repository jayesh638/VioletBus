package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.BusRating;

import java.util.Optional;

public interface BusRatingInterface {
    void saveBusRating(BusRating busRating);

    Optional<BusRating> findMyBusRating(long busId);
}
