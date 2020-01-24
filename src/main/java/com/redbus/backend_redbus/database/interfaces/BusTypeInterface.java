package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.BusType;

public interface BusTypeInterface {
    void saveBusType(BusType busType);

    void deleteBus(BusType busType);
}
