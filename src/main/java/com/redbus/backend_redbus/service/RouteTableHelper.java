package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.RouteTableInterface;
import com.redbus.backend_redbus.model.Bus;
import com.redbus.backend_redbus.model.PointsTable;
import com.redbus.backend_redbus.model.RouteTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class RouteTableHelper {
    @Autowired
    private RouteTableInterface routeTableInterface;
    Logger logger= LoggerFactory.getLogger(RouteTableHelper.class);
    public void saveRoute(List<String> routeAvailabilityDay, RouteTable routeTable, List<PointsTable> routePoints, Bus bus) throws Exception {
        String routeAvailability = "";
        for (String s : routeAvailabilityDay) {
            routeAvailability += s;
            routeAvailability+=",";
        }
        routeTable.setRouteAvailability(routeAvailability);
        routeTable.setRoutePoint(routePoints);
        try {
            routeTableInterface.saveRoute(routeTable);
        }catch (Exception e)
        {
            logger.error("Error while saving the route table",e);
            throw  new Exception(e);
        }
        bus.getRouteTable().add(routeTable);
    }
}
