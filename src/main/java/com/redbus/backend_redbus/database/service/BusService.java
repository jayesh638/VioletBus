package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.BusInterface;
import com.redbus.backend_redbus.model.Bus;
import com.redbus.backend_redbus.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BusService implements BusInterface {
    @Autowired
    private BusRepository busRepository;

    @Override
    public void saveBus(Bus bus) {
        busRepository.save(bus);
    }

    @Override
    public Bus findByBusNumber(String BusNumber) {
        return busRepository.findByNumber(BusNumber);
    }

    @Override
    public Bus findById(long id) {
        return busRepository.findById(id).get();
    }

    @Override
    public Optional<Bus> findByBusId(long busId) {
        return busRepository.findById(busId);
    }

    @Override
    public void deleteBus(Bus bus) {
        busRepository.delete(bus);
    }
}
