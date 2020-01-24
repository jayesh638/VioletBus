package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.UserTableInterface;
import com.redbus.backend_redbus.model.UserTable;
import com.redbus.backend_redbus.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserTableService implements UserTableInterface {
    @Autowired
    private UserTableRepository userTableRepository;

    @Override
    public void saveUserData(UserTable userTable) {
        userTableRepository.save(userTable);
    }

    @Override
    public UserTable findUserByUniqueId(String userId) {
        return userTableRepository.findByUniqueId(userId);
    }
}
