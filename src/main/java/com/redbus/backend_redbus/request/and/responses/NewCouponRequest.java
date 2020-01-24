package com.redbus.backend_redbus.request.and.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class NewCouponRequest {
    private String couponName;
    private String couponCode;
    private int offPercent;
    private int maxOffPrice;
    private String expirationDate;
}
