package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.BusInterface;
import com.redbus.backend_redbus.database.interfaces.SeatFareInterface;
import com.redbus.backend_redbus.database.interfaces.WeekDayInterface;
import com.redbus.backend_redbus.model.*;
import com.redbus.backend_redbus.repository.BusRepository;
import com.redbus.backend_redbus.repository.TicketRepository;
import com.redbus.backend_redbus.request.and.responses.CancellationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CancellationService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private BusRepository busRepository;
    @Autowired
    private WeekDayInterface weekDayInterface;
    @Autowired
    private SeatFareInterface seatFareInterface;

    Logger logger = LoggerFactory.getLogger(CancellationService.class);

    public void cancleTicket(CancellationRequest cancellationRequest) throws Exception {
        logger.info("inside cancleTicket Function");
        String ticketId = cancellationRequest.getTicketId();
        logger.info("ticketId:"+ticketId );
        String userEmail = cancellationRequest.getUserEmail();
        logger.info("userEmail:" +userEmail);
        logger.info("finding ticket");
        Ticket ticket = ticketRepository.findByIdAndEmailOnTicket(ticketId,userEmail);
        logger.info("ticketFound");
        ticket.setStatus("canceled");
        ticketRepository.save(ticket);

        logger.info("finding bus");
        Bus bus;
        try {
            logger.info("busId: "+ticket.getBusId());

            bus = busRepository.findById(Long.parseLong(ticket.getBusId())).get();
            logger.info("bus found");
            List<RouteTable> routeTables = bus.getRouteTable();
            logger.info("routes found");
            for(RouteTable routeTable:routeTables)
            {
                if(routeTable.getId()==Integer.parseInt(ticket.getRouteId()))
                {
                    logger.info("route id: "+ routeTable.getId());
                    List<WeekDays> weekDays = routeTable.getWeekDays();
                    for (WeekDays w : weekDays) {
                        if (w.getDayName().equals(ticket.getDayOfJourney())) {
                            logger.info("weekdays: "+w.getDayName());
                            WeekDays weekDays1 = weekDayInterface.findById(w.getId());
                            List<SeatFare> seatFares = weekDays1.getSeatFareList();
                                for (SeatFare s : seatFares) {

                                    if (s.getId()==Integer.parseInt(ticket.getSeatNumber())) {
                                        logger.info("seat: "+s.getId());
                                        SeatFare seatFare = seatFareInterface.findById(s.getId()).get();
                                        seatFare.setIsBooked("False");
                                        seatFare.setBookingGender("NA");
                                        seatFareInterface.saveSeatFare(seatFare);
                                    }
                                }

                            try {
                                weekDayInterface.saveWeekDay(weekDays1);
                            } catch (Exception e) {
                                throw new Exception(e);
                            }
                        }
                    }
                    try {
                        busRepository.save(bus);
                    } catch (Exception e) {
                        throw new Exception(e);
                    }
                }
            }
            logger.info("sending mail to the user");
            SendTicket sendTicket = new SendTicket();
            sendTicket.sendCancellationMail(cancellationRequest);
            logger.info("mail sent!");
        } catch (Exception e) {
            throw new Exception(e);
        }

    }
}
