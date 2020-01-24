package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.RoleInterface;
import com.redbus.backend_redbus.model.Role;
import com.redbus.backend_redbus.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleService implements RoleInterface {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}
