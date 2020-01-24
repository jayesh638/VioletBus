package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.model.Location;
import com.redbus.backend_redbus.model.UserTable;
import com.redbus.backend_redbus.repository.CouponRepository;
import com.redbus.backend_redbus.repository.LocationRepository;
import com.redbus.backend_redbus.repository.UserTableRepository;
import com.redbus.backend_redbus.request.and.responses.CityResponse;
import com.redbus.backend_redbus.request.and.responses.HomeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class HomeRequestService {
    @Autowired
    private UserTableRepository userTableRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private LocationRepository locationRepository;
    public ResponseEntity<?> homeService(Principal principal){
        HomeRequest homeRequest = new HomeRequest();
        List<CityResponse> cityResponses = new ArrayList<>();
        List<Location> locations = locationRepository.findAll();
        for (int i = 0; i < locations.size(); i++) {
            CityResponse cityResponse = new CityResponse();
            cityResponse.setId(locations.get(i).getId());
            cityResponse.setCityName(locations.get(i).getCityName());
            cityResponses.add(cityResponse);
        }
        homeRequest.setCityResponses(cityResponses);
        homeRequest.setCoupons(couponRepository.findAll());
        UserTable userTable = userTableRepository.findByUniqueId(principal.getName());
        if(userTable==null) {
            homeRequest.setUserId("");
        }else{
            homeRequest.setUserId(principal.getName());
        }
        return new ResponseEntity<>(homeRequest, HttpStatus.OK);
    }
}
