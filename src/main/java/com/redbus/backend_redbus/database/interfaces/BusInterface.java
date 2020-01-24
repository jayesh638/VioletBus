package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.Bus;

import java.util.Optional;


public interface BusInterface {
    void saveBus(Bus bus);

    Bus findByBusNumber(String BusNumber);

    Bus findById(long id);

    Optional<Bus> findByBusId(long busId);

    void deleteBus(Bus bus);
}
