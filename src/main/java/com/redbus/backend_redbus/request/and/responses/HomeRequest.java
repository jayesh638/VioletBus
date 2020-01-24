package com.redbus.backend_redbus.request.and.responses;

import com.redbus.backend_redbus.model.Coupon;
import com.redbus.backend_redbus.model.UserTable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class HomeRequest {
    private List<CityResponse> cityResponses;
    private String userId;
    private List<Coupon> coupons;
}
