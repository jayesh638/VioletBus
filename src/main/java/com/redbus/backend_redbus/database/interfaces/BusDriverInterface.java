package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.BusDriver;
import org.springframework.stereotype.Component;


public interface BusDriverInterface {
    void saveBusDriver(BusDriver busDriver);

    BusDriver findByName(String name);

    BusDriver findByNumber(String number);

    void deleteDriver(BusDriver busDriver);
}
