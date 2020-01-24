package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.PointsTableInterface;
import com.redbus.backend_redbus.model.PointsTable;
import com.redbus.backend_redbus.repository.PointsTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PointsTableService implements PointsTableInterface {
    @Autowired
    private PointsTableRepository pointsTableRepository;

    @Override
    public void savePoints(PointsTable pointsTable) {
        pointsTableRepository.save(pointsTable);
    }
}
