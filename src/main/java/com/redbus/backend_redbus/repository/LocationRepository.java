package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location,Integer> {
    Location findByCityName(String cityName);
}
