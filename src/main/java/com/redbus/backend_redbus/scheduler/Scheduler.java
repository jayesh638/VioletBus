package com.redbus.backend_redbus.scheduler;

import com.redbus.backend_redbus.database.interfaces.SeatFareInterface;
import com.redbus.backend_redbus.model.SeatFare;
import com.redbus.backend_redbus.model.Ticket;
import com.redbus.backend_redbus.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private SeatFareInterface seatFareInterface;
    Logger logger= LoggerFactory.getLogger(Scheduler.class);
    @Scheduled(fixedRate = 500000*12*24)
    public void resetTicket() {
        logger.info("Starting background task of clearing tickets");
        Date date = new Date();
        List<Ticket> ticketList= ticketRepository.findAll();
        for(Ticket ticket:ticketList)
        {
            String bookingDate=ticket.getDateOfJourney();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String strDate= formatter.format(date);
            if(Integer.parseInt(strDate.substring(8,10))>Integer.parseInt(bookingDate.substring(8,10)))
            {
            SeatFare seatFare =seatFareInterface.findById(Integer.parseInt(ticket.getSeatNumber())).get();
            seatFare.setIsBooked("False");
            seatFare.setBookingGender("NA");
            seatFareInterface.saveSeatFare(seatFare);
            logger.info("seat info updated for seatId "+seatFare.getId());
            }
        }
        logger.info("Stopping background task");
    }
}
