package com.redbus.backend_redbus.controller;


import com.paytm.pg.merchant.CheckSumServiceHelper;
import com.redbus.backend_redbus.model.PaytmDetails;
import com.redbus.backend_redbus.request.and.responses.*;
import com.redbus.backend_redbus.request.and.responses.ResponseBody;
import com.redbus.backend_redbus.service.BusServiceHelper;
import com.redbus.backend_redbus.service.RatingAndReviewServiceHelper;
import com.redbus.backend_redbus.service.RouteServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.*;

@RestController
@CrossOrigin
public class BusController {
    @Autowired
    private BusServiceHelper busServiceHelper;
    @Autowired
    private RouteServiceHelper routeServiceHelper;
    @Autowired
    private RatingAndReviewServiceHelper ratingAndReviewServiceHelper;
    @Autowired
    private PaytmDetails paytmDetails;
    private Logger logger = LoggerFactory.getLogger(BusController.class);

    @PostMapping("/addBus")
    public ResponseEntity<?> addBus(@RequestBody RequestBusAdd requestBusAdd) {
        try {
            String response = busServiceHelper.addBus(requestBusAdd);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while saving the Bus", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(name = "fromCityName") String fromCity, @RequestParam(name = "toCityName") String toCityName, @RequestParam(name = "departureDate") String departureDate,
                                    @RequestParam(name = "returnDate", required = false) String returnDate) throws ParseException {
        List<ResponseBody> responseBodyList = busServiceHelper.searchBus(fromCity, toCityName, departureDate);
        return ResponseEntity.ok(responseBodyList);
    }

    @PutMapping("/editBus")
    public ResponseEntity<?> editBus(@RequestBody EditBusRequest editBusRequest) {
        String response = busServiceHelper.editBus(editBusRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/addRoute")
    public ResponseEntity<?> addRoute(@RequestBody RequestRouteAdd requestRouteAdd) {
        try {
            String response = routeServiceHelper.addRoute(requestRouteAdd);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while saving the route in the bus", HttpStatus.OK);
        }
    }

    @PostMapping("/rateMyBus")
    public ResponseEntity<?> rateBus(@RequestBody RequestBusReview requestBusReview) {
        try {
            String response = ratingAndReviewServiceHelper.addRating(requestBusReview);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while saving your review", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/pay")
    public ModelAndView getRedirect(@RequestBody BookingRequest bookingRequest, HttpServletResponse httpServletResponse) throws Exception {
//       httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
//       httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST");
//       httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
        ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetails.getPaytmUrl());
        TreeMap<String, String> parameters = new TreeMap<>();
        modelAndView.addObject("booked", bookingRequest);
        paytmDetails.getDetails().forEach((k, v) -> parameters.put(k, v));
        logger.info(bookingRequest.getEmailOnTicket());
        parameters.put("MOBILE_NO", bookingRequest.getPhoneOnTicket());
        logger.info("payment session");
        parameters.put("EMAIL", bookingRequest.getEmailOnTicket());
        parameters.put("ORDER_ID", UUID.randomUUID().toString());
        parameters.put("TXN_AMOUNT", bookingRequest.getFare());
        parameters.put("CUST_ID", bookingRequest.getUserId());
        String checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(paytmDetails.getMerchantKey(), parameters);
        parameters.put("CHECKSUMHASH", checkSum);
        modelAndView.addAllObjects(parameters);
        return modelAndView;
    }

    @GetMapping(value = "/payNow")
    public ModelAndView getRedirect(HttpServletRequest httpSession, HttpServletResponse httpServletResponse) throws Exception {
//        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
        ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetails.getPaytmUrl());
        TreeMap<String, String> parameters = new TreeMap<>();
        BookingRequest bookingRequest = (BookingRequest) httpSession.getSession().getAttribute("bookingRequest");
        paytmDetails.getDetails().forEach((k, v) -> parameters.put(k, v));
        logger.info("Payment gateway ");
        logger.info(bookingRequest.getEmailOnTicket());
        parameters.put("MOBILE_NO", bookingRequest.getPhoneOnTicket());
        logger.info("payment session");
        parameters.put("EMAIL", bookingRequest.getEmailOnTicket());
        parameters.put("ORDER_ID", UUID.randomUUID().toString());
        parameters.put("TXN_AMOUNT", bookingRequest.getFare());
        parameters.put("CUST_ID", bookingRequest.getUserId());
        String checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(paytmDetails.getMerchantKey(), parameters);
        parameters.put("CHECKSUMHASH", checkSum);
        modelAndView.addAllObjects(parameters);
        return modelAndView;
    }

    @PostMapping("/response")
    public ModelAndView getResponseRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
//        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
        System.out.println("Inside Response");
        logger.info("Inside response");
        Map<String, String[]> mapData = request.getParameterMap();
        BookingRequest bookingRequest1 = (BookingRequest) request.getAttribute("booked");
        System.out.println(bookingRequest1.getDayOfJourney());
        TreeMap<String, String> parameters = new TreeMap<String, String>();
        logger.info("now getting the data from session");
//        BookingRequest bookingRequest1= (BookingRequest) request.getSession().getAttribute("bookingRequest");
        logger.info("Printing data from request");
        mapData.forEach((key, val) -> parameters.put(key, val[0]));
        String paytmChecksum = "";
        if (mapData.containsKey("CHECKSUMHASH")) {
            paytmChecksum = mapData.get("CHECKSUMHASH")[0];
        }
        String result;
        boolean isValideChecksum = false;
        System.out.println("RESULT : " + parameters.toString());
        try {
            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
                if (parameters.get("RESPCODE").equals("01")) {
                    logger.info("Payment success-full");
                    ModelAndView modelAndView = new ModelAndView("redirect:" + "/addBooking/paytm");
                    request.getSession().setAttribute("result", parameters);
                    return modelAndView;
                } else {
                    logger.info("Payment failed");
                    ModelAndView modelAndView = new ModelAndView("redirect:" + "/addBooking/paytm");
                    request.getSession().setAttribute("result", parameters);
                    return modelAndView;
                }
            } else {
                result = "Checksum mismatched";
            }
        } catch (Exception e) {
            result = e.toString();
        }
        ModelAndView modelAndView = new ModelAndView("redirect:" + "/bookNow");
        modelAndView.addObject("payment", "ok");
        return modelAndView;

    }

    private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
        return CheckSumServiceHelper.getCheckSumServiceHelper().verifycheckSum(paytmDetails.getMerchantKey(),
                parameters, paytmChecksum);
    }
}


