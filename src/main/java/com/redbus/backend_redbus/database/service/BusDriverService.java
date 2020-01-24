package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.BusDriverInterface;
import com.redbus.backend_redbus.model.BusDriver;
import com.redbus.backend_redbus.repository.BusDriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusDriverService implements BusDriverInterface {

    @Autowired
    private BusDriverRepository busDriverRepository;

    @Override
    public void saveBusDriver(BusDriver busDriver) {
        busDriverRepository.save(busDriver);
    }

    @Override
    public BusDriver findByName(String name) {
        return busDriverRepository.findByDriverName(name);
    }

    @Override
    public BusDriver findByNumber(String number) {
        return busDriverRepository.findByDriverPhoneNumber(number);
    }

    @Override
    public void deleteDriver(BusDriver busDriver) {
        busDriverRepository.delete(busDriver);
    }
}
