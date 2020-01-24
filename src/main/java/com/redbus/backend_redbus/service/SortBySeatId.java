package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.model.SeatFare;
import org.springframework.stereotype.Component;

import java.util.Comparator;
@Component
class SortBySeatId implements Comparator<SeatFare>
    {
        @Override
        public int compare(SeatFare seatFare, SeatFare t1) {
            return seatFare.getId()-t1.getId();
        }
    }

