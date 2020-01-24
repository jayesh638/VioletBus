package com.redbus.backend_redbus.database.interfaces;

import com.redbus.backend_redbus.model.UserTable;
import org.apache.catalina.User;

import java.util.Optional;

public interface UserTableInterface {
    void saveUserData(UserTable userTable);

    UserTable findUserByUniqueId(String userId);
}
