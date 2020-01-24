package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;


public interface BusRepository extends JpaRepository<Bus, Long> {
    Bus findByNumber(@NotNull String number);
}
