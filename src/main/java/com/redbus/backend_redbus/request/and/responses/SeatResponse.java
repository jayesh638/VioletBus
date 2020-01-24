package com.redbus.backend_redbus.request.and.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class SeatResponse {
    private String windowSeatPrice;
    private String sittingSeatPrice;
    private String sleeperSeatPrice;
}
