package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.BusTypeInterface;
import com.redbus.backend_redbus.model.BusType;
import com.redbus.backend_redbus.repository.BusTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusTypeService implements BusTypeInterface {


    @Autowired
    private BusTypeRepository busTypeRepository;

    @Override
    public void saveBusType(BusType busType) {
        busTypeRepository.save(busType);
    }

    @Override
    public void deleteBus(BusType busType) {
        busTypeRepository.delete(busType);
    }
}
