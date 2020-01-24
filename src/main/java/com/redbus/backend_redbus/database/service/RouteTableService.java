package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.RouteTableInterface;
import com.redbus.backend_redbus.model.RouteTable;
import com.redbus.backend_redbus.repository.RouteTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;

@Component
public class RouteTableService implements RouteTableInterface {
    @Autowired
    private RouteTableRepository routeTableRepository;

    @Override
    public void saveRoute(RouteTable routeTable) {
        routeTableRepository.save(routeTable);
    }

    @Override
    public List<RouteTable> findRouteByCityNames(String origin, String destination) {
        return routeTableRepository.findByStartingPointAndEndingPoint(origin, destination);
    }

    @Override
    public void deleteRouteTable(RouteTable routeTable) {
        routeTableRepository.delete(routeTable);
    }
}
