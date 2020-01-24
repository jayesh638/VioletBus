package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.TicketInterface;
import com.redbus.backend_redbus.model.Ticket;
import com.redbus.backend_redbus.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketService implements TicketInterface {
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
