package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByUserRole(String userRole);
}
