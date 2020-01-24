package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.UserTableInterface;
import com.redbus.backend_redbus.model.Ticket;
import com.redbus.backend_redbus.model.UserTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTicketService {
    @Autowired
    private UserTableInterface userTableInterface;
    public List<Ticket> printTickets(String id) {
        UserTable userTable = userTableInterface.findUserByUniqueId(id);
        List<Ticket> tickets = userTable.getTickets();
        return tickets;
    }
}
