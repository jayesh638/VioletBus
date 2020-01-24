package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.BusDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusDriverRepository extends JpaRepository<BusDriver, Long> {
    BusDriver findByDriverName(String busDriverName);

    BusDriver findByDriverPhoneNumber(String busDriverNumber);
}
