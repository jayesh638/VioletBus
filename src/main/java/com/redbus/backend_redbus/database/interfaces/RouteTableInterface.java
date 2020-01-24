package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.RouteTable;

import java.util.LinkedHashSet;
import java.util.List;

public interface RouteTableInterface {
    void saveRoute(RouteTable routeTable);

    List<RouteTable> findRouteByCityNames(String origin, String destination);

    void deleteRouteTable(RouteTable routeTable);
}
