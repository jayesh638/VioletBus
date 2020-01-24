package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.LocationInterface;
import com.redbus.backend_redbus.model.Location;
import com.redbus.backend_redbus.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LocationService implements LocationInterface {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void saveLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public Location findByCityName(String cityName) {
        return locationRepository.findByCityName(cityName);
    }
}
