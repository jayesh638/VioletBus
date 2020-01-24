package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.RoleInterface;
import com.redbus.backend_redbus.database.interfaces.UserTableInterface;
import com.redbus.backend_redbus.model.Role;
import com.redbus.backend_redbus.model.Ticket;
import com.redbus.backend_redbus.model.UserTable;
import com.redbus.backend_redbus.request.and.responses.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserService {

    @Autowired
    private UserTableInterface userTableInterface;
    @Autowired
    private RoleInterface roleInterface;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public void addUser(UserRequest userRequest) throws Exception {
        logger.info("inside addUser method");
        UserTable userTable = new UserTable();
        userTable.setPassword(userRequest.getPassword());
        Role role = new Role();
        role.setUserRole(userRequest.getRole());

        logger.info("saving role using role repository");
        try {
            roleInterface.saveRole(role);
        } catch (Exception e) {
            logger.error("Exception occured while saving role :" + e.getMessage());
            throw new Exception(e);
        }
        logger.info("role saved by role repository");

        userTable.setRoleId(role);

        if (userRequest.getIsEnable().equals("T")) {
            userTable.setEnable(true);
        } else {
            userTable.setEnable(false);
        }

        userTable.setName(userRequest.getName());
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse(userRequest.getCreateDate());
        userTable.setCreatedDate(date);

        userTable.setEmail(userRequest.getEmail());
        List<Ticket> ticket = new ArrayList<>();
        userTable.setTickets(ticket);

        logger.info("saving user table");
        try {
            userTableInterface.saveUserData(userTable);
        } catch (Exception e) {
            logger.error("Exception occured while saving usertable :" + e.getMessage());
            throw new Exception(e);
        }
    }
}
