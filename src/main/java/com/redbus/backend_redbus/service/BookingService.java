package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.*;
import com.redbus.backend_redbus.model.*;
import com.redbus.backend_redbus.request.and.responses.BookingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Component
public class BookingService {

    Logger logger = LoggerFactory.getLogger(BookingService.class);
    @Autowired
    private UserTableInterface userTableInterface;
    @Autowired
    private TicketInterface ticketInterface;
    @Autowired
    private BusInterface busInterface;
    @Autowired
    private WeekDayInterface weekDayInterface;
    @Autowired
    private SeatFareInterface seatFareInterface;

    public void addBooking(BookingRequest bookingRequest) throws Exception {
        logger.info("inside addBooking method");
        System.out.println(bookingRequest.getPassengers().size());
        LinkedList<String> tickletId = new LinkedList<>();
        for(int j=0;j<bookingRequest.getPassengers().size();j++)
        {
            System.out.println(bookingRequest.getPassengers().get(j).getPassengerName());
            System.out.println(bookingRequest.getPassengers().get(j).getPassengerGender());
            System.out.println(bookingRequest.getPassengers().get(j).getPassengerAge());
            System.out.println(bookingRequest.getPassengers().get(j).getPassengerSeatId());
            Ticket ticket = new Ticket();
            UUID uuid = UUID.randomUUID();
            ticket.setId(uuid.toString());
            bookingRequest.getPassengers().get(j).setTicketId(uuid.toString());
            tickletId.add(uuid.toString());
            ticket.setAgeOnTicket(bookingRequest.getPassengers().get(j).getPassengerAge());

            ticket.setBusName(bookingRequest.getBusName());
            ticket.setBusId(bookingRequest.getBusId());

            ticket.setStatus("active");

            ticket.setRouteId(bookingRequest.getRouteId());

            ticket.setDateOfBooking(bookingRequest.getDateOfBooking());
            ticket.setDayOfBooking(bookingRequest.getDayOfBooking());
            ticket.setDateOfJourney(bookingRequest.getDateOfJourney());
            ticket.setDayOfJourney(bookingRequest.getDayOfJourney());

            ticket.setDroppingPoint(bookingRequest.getDroppingPoint());
            ticket.setDroppingTime(bookingRequest.getDroppingTime());
            ticket.setBoardingPoint(bookingRequest.getBoardingPoint());
            ticket.setBoardingTime(bookingRequest.getBoardingTime());

            ticket.setEmailOnTicket(bookingRequest.getEmailOnTicket());
            ticket.setFare(bookingRequest.getFare());
            ticket.setGenderOnTicket(bookingRequest.getPassengers().get(j).getPassengerGender());
            ticket.setNameOnTicket(bookingRequest.getPassengers().get(j).getPassengerName());
            ticket.setPhoneOnTicket(bookingRequest.getPhoneOnTicket());
            ticket.setPhoneOnTicket(bookingRequest.getPhoneOnTicket());

            ticket.setSeatNumber(bookingRequest.getPassengers().get(j).getPassengerSeatId());

            logger.info("finding usertable using userTableRepository");
            UserTable userTable;
            try {
                userTable = userTableInterface.findUserByUniqueId(bookingRequest.getUserId());
            } catch (Exception e) {
                logger.error("Exception occured while saving usertable. " + e.getMessage());
                throw new Exception(e);
            }
            logger.info("UserTable saved");

            userTable.getTickets().add(ticket);

            ticket.setUser(userTable);

            logger.info("Saving Ticket");
            try {
                ticketInterface.saveTicket(ticket);
            } catch (Exception e) {
                logger.error("Exception occured while saving ticket. " + e.getMessage());
                throw new Exception(e);
            }
            logger.info("ticket saved");
        }

        logger.info("finding bus");
        Bus bus;
        try {
            System.out.println(bookingRequest.getBusId());
            bus = busInterface.findById(Long.parseLong(bookingRequest.getBusId()));
            logger.info("bus found");
            System.out.println(bus.getName());

            List<RouteTable> routeTables = bus.getRouteTable();
            System.out.println(routeTables.size());

            for (int i=0;i<routeTables.size();i++)
            {
                System.out.println(routeTables.get(i).getRouteName());
            }
            LinkedList<String> seatnames = new LinkedList<>();
            for(RouteTable routeTable:routeTables)
            {
                if(routeTable.getId()==Integer.parseInt(bookingRequest.getRouteId()))
                {
                    logger.info("finding list of weekdays");
                    List<WeekDays> weekDays = routeTable.getWeekDays();
                    logger.info("list days found");
                    for (WeekDays w : weekDays) {
                        logger.info("inside for loop:" + w.getDayName());
                        if (w.getDayName().equals(bookingRequest.getDayOfJourney())) {
                            logger.info("inside if:" + w.getDayName());
                            String day = w.getDayName();
                            logger.info("weekday Repository");
                            WeekDays weekDays1 = weekDayInterface.findById(w.getId());
                            logger.info("weekday found");
                            List<SeatFare> seatFares = weekDays1.getSeatFareList();
                            for(int i =0 ;i<bookingRequest.getPassengers().size();i++)
                            {
                                for (SeatFare s : seatFares) {
                                    if (s.getId()==Integer.parseInt(bookingRequest.getPassengers().get(i).getPassengerSeatId())) {
                                        logger.info("finding seatfare" + s.getSeatName());
                                        SeatFare seatFare = seatFareInterface.findById(s.getId()).get();
                                        seatFare.setIsBooked("True");
                                        seatnames.add(seatFare.getSeatName());
                                        seatFare.setBookingGender(bookingRequest.getPassengers().get(i).getPassengerGender());
                                        seatFareInterface.saveSeatFare(seatFare);
                                    }
                                }
                            }
                            try {
                                weekDayInterface.saveWeekDay(weekDays1);
                            } catch (Exception e) {
                                logger.error("error while saving weekdays");
                                throw new Exception(e);
                            }
                        }
                    }
                    logger.info("saving bus");
                    try {
                        busInterface.saveBus(bus);
                    } catch (Exception e) {
                        logger.error("Error while saving bus. " + e.getMessage());
                        throw new Exception(e);
                    }
                    logger.info("bus saved");
                }
            }
            logger.info("sending mail to the user");
            try{
                SendTicket sendTicket = new SendTicket();
                sendTicket.sendmail(bookingRequest,seatnames,tickletId);
            }catch (Exception e)
            {
                logger.info(e.getMessage());
            }

            logger.info("mail sent!");
        } catch (Exception e) {
            logger.info("Exception occured while getting bus. " + e.getMessage());
            throw new Exception(e);
        }
    }
}