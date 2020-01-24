package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.UserTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTableRepository extends JpaRepository<UserTable,Long> {

    UserTable findByEmail(String email);
    UserTable findByUniqueId(String uniqueId);
    UserTable findByName(String name);
}
