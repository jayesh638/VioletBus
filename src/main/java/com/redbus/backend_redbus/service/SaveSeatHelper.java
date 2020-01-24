package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.database.interfaces.SeatFareInterface;
import com.redbus.backend_redbus.model.SeatFare;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class SaveSeatHelper {

    private Logger logger= LoggerFactory.getLogger(SaveSeatHelper.class);
    @Autowired
    private SeatFareInterface seatFareInterface;

    private String busSeatGenerator(String name,int sequence)
    {
        return name+sequence;
    }
    public List<SeatFare> saveSeat(int totalNonWindowsSittingSeat, int nonWindowSittingSeatsPrice,
                                    int totalWindowsSittingSeat, int windowSittingSeatsPrice,
                                    int totalWindowSleeperSeat, int windowSleeperSeatsPrice,
                                    int nonWindowSleeperSeatsPrice, int totalNonWindowsSleeperSeat, String busName) throws Exception {
        logger.info("--> Inside Save Seat Function Save seat helper class <--");
        List<SeatFare> seatFareList= new ArrayList<>();
        for (int i = 0; i < totalNonWindowsSittingSeat; i++) {
            logger.info("--> Generating Non-WindowSittingSeat for Bus <--");
            SeatFare seatFare = new SeatFare();
            seatFare.setIsBooked("False");
            seatFare.setBookingGender("NA");
            seatFare.setSeatType("Sitting");
            seatFare.setSeatName(busSeatGenerator(busName.substring(0,3).toUpperCase(),i));
            seatFare.setSeatPrice(nonWindowSittingSeatsPrice);
            seatFare.setIsWindowSeat("No");
            logger.info("--> Saving Data to the seatTable <--");
            try {
                seatFareInterface.saveSeatFare(seatFare);
            }catch (Exception e)
            {
                logger.error("Error Occurred while saving the NonWindowSeatingSeat",e);
                throw  new Exception(e);
            }
            logger.info("--> Seat Saved Successfully <--");
            seatFareList.add(seatFare);
            logger.info("--> seat now added to overall list <--");
        }
        for (int i = 0; i < totalWindowsSittingSeat; i++) {
            logger.info("--> Generating WindowSittingSeat for Bus <--");
            int j=totalNonWindowsSittingSeat+i;
            SeatFare seatFare = new SeatFare();
            seatFare.setIsBooked("False");
            seatFare.setBookingGender("NA");
            seatFare.setSeatType("Sitting");
            seatFare.setSeatName(busSeatGenerator(busName.substring(0,3).toUpperCase(),j));
            seatFare.setSeatPrice(windowSittingSeatsPrice);
            seatFare.setIsWindowSeat("Yes");
            logger.info("--> Saving Data to the seatTable <--");
            try {
                seatFareInterface.saveSeatFare(seatFare);
            }catch (Exception e)
            {
                logger.error("Error while saving WindowSeatingSeat",e);
                throw  new Exception(e);
            }
            logger.info("--> seat now added to overall list <--");
            seatFareList.add(seatFare);
        }
        for (int i = 0; i < totalWindowSleeperSeat; i++) {
            logger.info("--> Generating WindowSleeperSeat for Bus <--");
            int j=totalNonWindowsSittingSeat+totalWindowsSittingSeat+i;
            SeatFare seatFare = new SeatFare();
            seatFare.setIsBooked("False");
            seatFare.setBookingGender("NA");
            seatFare.setSeatType("Sleeper");
            seatFare.setSeatName(busSeatGenerator(busName.substring(0,3).toUpperCase(),j));
            seatFare.setSeatPrice(windowSleeperSeatsPrice);
            seatFare.setIsWindowSeat("Yes");
            logger.info("--> Saving Data to the seatTable <--");
            try {
                seatFareInterface.saveSeatFare(seatFare);
            }catch (Exception e)
            {
                logger.error("Error while saving the WindowSleeperSeats",e);
                throw  new Exception(e);
            }
            logger.info("--> seat now added to overall list <--");
            seatFareList.add(seatFare);
        }
        for (int i = 0; i < totalNonWindowsSleeperSeat; i++) {
            logger.info("--> Generating NonWindowSleeperSeat for Bus <--");
            int j=totalNonWindowsSittingSeat+totalWindowsSittingSeat+totalWindowSleeperSeat+i;
            SeatFare seatFare = new SeatFare();
            seatFare.setIsBooked("False");
            seatFare.setBookingGender("NA");
            seatFare.setSeatType("Sleeper");
            seatFare.setSeatName(busSeatGenerator(busName.substring(0,3).toUpperCase(),j));
            seatFare.setSeatPrice(nonWindowSleeperSeatsPrice);
            seatFare.setIsWindowSeat("Yes");
            logger.info("--> Saving Data to the seatTable <--");
            try {
                seatFareInterface.saveSeatFare(seatFare);
            }catch (Exception e)
            {
                logger.error("Error while saving the NonWindowSleeperSeat",e);
                throw  new Exception(e);
            }
            logger.info("--> seat now added to overall list <--");
            seatFareList.add(seatFare);
        }
        return seatFareList;
    }
}
