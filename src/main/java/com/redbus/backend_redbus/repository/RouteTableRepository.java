package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.RouteTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;

@Repository
public interface RouteTableRepository extends JpaRepository<RouteTable,Long> {
    List<RouteTable> findByStartingPointAndEndingPoint(String startingCity, String destinationCity);

}
