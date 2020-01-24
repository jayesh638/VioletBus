package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.Location;

public interface LocationInterface {
    void saveLocation(Location location);

    Location findByCityName(String cityName);
}
