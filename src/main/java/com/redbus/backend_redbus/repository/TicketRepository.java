package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,String> {
    Ticket findByIdAndEmailOnTicket(String id, String emailOnTicket);
    Ticket findByBusIdAndSeatNumber(String busId, String seatNumber);
}
