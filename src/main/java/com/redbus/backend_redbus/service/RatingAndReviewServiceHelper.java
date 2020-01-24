package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.BusInterface;
import com.redbus.backend_redbus.database.interfaces.BusRatingInterface;
import com.redbus.backend_redbus.database.interfaces.UserRatingInterface;
import com.redbus.backend_redbus.database.interfaces.UserTableInterface;
import com.redbus.backend_redbus.model.*;
import com.redbus.backend_redbus.request.and.responses.RequestBusReview;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RatingAndReviewServiceHelper {
    @Autowired
    private ValidityChecker validityChecker;
    @Autowired
    private BusInterface busInterface;
    @Autowired
    private UserTableInterface userTableInterface;
    @Autowired
    private UserRatingInterface userRatingInterface;
    @Autowired
    private BusRatingInterface busRatingInterface;

    public String addRating(RequestBusReview requestBusReview) {
        if (validityChecker.checkReviewValidity(requestBusReview))
            return "Incorrect data format";

        Optional<Bus> bus = busInterface.findByBusId(requestBusReview.getBusId());
        if (!bus.isPresent())
            return "No bus found for this busId";
        Bus bus1= bus.get();
        UserTable userTable = userTableInterface.findUserByUniqueId(requestBusReview.getUserId());
        if (userTable==null)
            return "User not found in the database! Are you registered";
        UserTable userTable1=userTable;
        List<Ticket> ticketList=userTable1.getTickets();
        boolean valid=false;
        for(Ticket ticket:ticketList)
        {
            if(Long.parseLong(ticket.getBusId())==bus1.getId())
            {
                valid=true;
                break;
            }
        }
        if(!valid)
        {
           return "You have not travelled from this bus yet!!";
        }
        UserRatingReview userRatingReview = new UserRatingReview();
        userRatingReview.setBusId(bus1.getId());
        userRatingReview.setUserRating(requestBusReview.getUserRating());
        userRatingReview.setUserTable(userTable);
        userRatingReview.setUserReview(requestBusReview.getUserReview());
        userRatingInterface.saveRating(userRatingReview);
        System.out.println(bus1.getId());
        BusRating busRating=bus1.getBusRating();
        busRating.setTotalRated(busRating.getTotalRated()+userRatingReview.getUserRating());
        busRating.setTotalRating(busRating.getTotalRating()+1);
        busRating.setOverallRating(busRating.getTotalRated()/busRating.getTotalRating());
        busRatingInterface.saveBusRating(busRating);
        return "Rating has been updated";
    }
}
