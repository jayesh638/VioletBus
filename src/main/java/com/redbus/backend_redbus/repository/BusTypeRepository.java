package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.BusType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BusTypeRepository extends JpaRepository<BusType,Integer> {
}