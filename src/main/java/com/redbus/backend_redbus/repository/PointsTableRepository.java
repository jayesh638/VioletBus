package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.PointsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointsTableRepository extends JpaRepository<PointsTable,Long> {
}
