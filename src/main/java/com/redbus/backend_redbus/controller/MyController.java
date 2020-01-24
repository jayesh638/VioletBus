package com.redbus.backend_redbus.controller;

import com.redbus.backend_redbus.model.*;
import com.redbus.backend_redbus.repository.*;
import com.redbus.backend_redbus.request.and.responses.*;
import com.redbus.backend_redbus.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@RestController
@CrossOrigin
public class MyController {

    Logger logger = LoggerFactory.getLogger(MyController.class);
    @Autowired
    private BookingService bookingService;
    @Autowired
    private UserTicketService userTicketService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CancellationService cancellationService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private HomeRequestService homeRequestService;

    @GetMapping("/")
    public ResponseEntity<?> showhome(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        logger.info("home page shown");
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity<?> responseEntity = homeRequestService.homeService(principal);
        return responseEntity;
    }

    @GetMapping("/userDetails")
    public ResponseEntity<?> showUser() {
        logger.info("User page shown");
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity<>("userId: " + principal.getName(), HttpStatus.OK);
    }

    @GetMapping("/userlogin")
    public ModelAndView showUser(Principal principal, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        ModelAndView modelAndView = new ModelAndView();
        if (principal == null) {
            modelAndView.setViewName("redirect:/userlogin");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @GetMapping("/userlogout")
    public ModelAndView logoutUser(Principal principal) {
        if (principal == null)
            return new ModelAndView("redirect:/");
        return new ModelAndView("redirect:/");
    }

    @GetMapping("/cities")
    public List<Location> listAllCitiesWithLandmarks() {
        logger.info("get all cities with boarding and dropping point");
        return locationRepository.findAll();
    }

    @GetMapping("/city")
    public ResponseEntity<?> getAllCity() {
        logger.info("listing all cities");
        List<CityResponse> cityResponses = new ArrayList<>();
        List<Location> locations = locationRepository.findAll();
        for (int i = 0; i < locations.size(); i++) {
            CityResponse cityResponse = new CityResponse();
            cityResponse.setId(locations.get(i).getId());
            cityResponse.setCityName(locations.get(i).getCityName());
            cityResponses.add(cityResponse);
        }
        return new ResponseEntity(cityResponses, HttpStatus.OK);
    }

    @PostMapping("/cancelTicket")
    public ResponseEntity<?> cancleTicket(@RequestBody CancellationRequest cancellationRequest) {
        try {
            cancellationService.cancleTicket(cancellationRequest);
            return new ResponseEntity<>("Ticket cancelled", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Problem on cancelling ticket", HttpStatus.BAD_REQUEST);
        }

        /*{
              "ticketId":"20",
              "userEmail":"raj@gmail.com"
          }*/
    }


    @PostMapping("/addBooking")
    public ResponseEntity<?> addBooking(@RequestBody BookingRequest bookingRequest, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) throws IOException {
        if (bookingRequest.getPaymentMethod().equals("card"))
            try {
                bookingService.addBooking(bookingRequest);
                return new ResponseEntity<>(bookingRequest, HttpStatus.OK);

            } catch (Exception e) {
                return new ResponseEntity<>("Booking cannot be done right now!", HttpStatus.BAD_REQUEST);
            }
        else {
            logger.info("payment gateway initiated");
            httpServletRequest.getSession().setAttribute("bookingRequest", bookingRequest);
            httpServletResponse.sendRedirect("/payNow");
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/addBooking/paytm")
    public ResponseEntity<?> addBooking2(HttpServletRequest httpServletRequest) throws IOException {
        try {
            TreeMap<String, String> parameters = new TreeMap<String, String>();
            parameters = (TreeMap<String, String>) httpServletRequest.getSession().getAttribute("result");

            BookingRequest bookingRequest = (BookingRequest) httpServletRequest.getSession().getAttribute("bookingRequest");
            bookingRequest.setTxnamount(parameters.get("TXNAMOUNT"));
            bookingRequest.setStatus(parameters.get("STATUS"));
            bookingRequest.setRespmsg(parameters.get("RESPMSG"));
            bookingRequest.setRespcode(parameters.get("RESPCODE"));
            bookingRequest.setOrderid(parameters.get("ORDERID"));
            bookingRequest.setMid(parameters.get("MID"));
            bookingRequest.setCurrency(parameters.get("CURRENCY"));
            bookingService.addBooking(bookingRequest);

            httpServletRequest.getSession().removeAttribute("result");
            httpServletRequest.getSession().removeAttribute("bookingRequest");
            httpServletRequest.getSession().invalidate();
            return new ResponseEntity<>(bookingRequest, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Booking cannot be done right now!", HttpStatus.BAD_REQUEST);
        }
    }

        /*{
<<<<<<< HEAD
            "busName":"Bhanu Pratap Travels",
            "userId":"1",
            "busId":"9",
            "routeId":"1",
            "emailOnTicket":"raj@gmail.com",
            "phoneOnTicket":"4568653214",
            "fare":"1300",
            "dateOfJourney":"2020-01-14",
            "dayOfJourney":"Tuesday",
            "dateOfBooking":"2020-01-17",
            "dayOfBooking":"Friday",
            "boardingPoint":"Near A Mall",
            "droppingPoint":"ABC Bus Station",
            "boardingTime":"07:30 PM",
            "droppingTime":"06:00 AM",
            "passengers":[
                {
                    "passengerName":"Jayesh",
                    "passengerGender":"Male",
                    "passengerAge":"70",
                    "passengerSeatId":"4"
                },
                {
                        "passengerName":"Jayesh",
                    "passengerGender":"Male",
                    "passengerAge":"71",
                    "passengerSeatId":"5"
                }
            ]
        }*/


   /* @PostMapping("/addUser")
    private ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) throws ParseException {
        try {
            userService.addUser(userRequest);
            return new ResponseEntity<>("User Added Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error while saving user ", HttpStatus.BAD_REQUEST);
        }

        *//*{
            "name":"jayesh",
            "password":"123",&
            "email":"jayesh@gmail.com",
            "isEnable":"T",
            "createDate":"2020-01-16",
            "role":"user"
        }*//*
    }*/

    @GetMapping("/ticket/{userId}")
    private ResponseEntity<?> printUserTickets(@PathVariable("userId") String id) {
        List<Ticket> list = userTicketService.printTickets(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/coupon")
    public ResponseEntity<?> applyCoupon(@RequestBody CouponRequest couponRequest) {
        ResponseEntity<?> responseEntity = new ResponseEntity<>("Invalid Code", HttpStatus.BAD_REQUEST);
        if (couponService.isValidCoupon(couponRequest)) {
            Coupon coupon = couponService.getCouponDetails(couponRequest);
            responseEntity = new ResponseEntity<>(coupon, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>("Coupon either expired or is invalid!", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping("/addCoupon")
    public void addCouponManually() throws ParseException {
        couponService.addCouponManually();
    }

    @PostMapping("/coupon")
    public ResponseEntity<?> addCoupon(@RequestBody NewCouponRequest coupon) {
        ResponseEntity<?> responseEntity = new ResponseEntity<>("Coupon cannot be added.", HttpStatus.BAD_REQUEST);
        try {
            couponService.addCoupon(coupon);
            responseEntity = new ResponseEntity<>("Coupon Added Succesfully", HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>("Coupon Not Added", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @GetMapping("/allCoupons")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(couponService.getAllCoupons(), HttpStatus.OK);
    }
    /*@RequestMapping(value = "/sendemail")
    public String sendEmail() throws AddressException, MessagingException, IOException {
        SendTicket sendTicket = new SendTicket();
        sendTicket.sendmail();
        return "Ticket sent to your mail!";
    }*/
}

/*addbus
  {
    "busName": "VIP Premium Volvo",
    "busNumber": "DLSH-09-3256",
    "busDriver": "Gaurav RockFord",
    "busType": "AC",
    "busTotalSeat":"20",
    "totalWindowSleeperSeats": 5,
    "totalNonWindowSleeperSeats" :5,
    "totalWindowSeatingSeats" :5,
    "totalNonWindowSeatingSeats": 5,
    "startingPrice":1250,
    "sleeperAvailable":true,
    "windowSleeperSeatsPrice":0,
    "windowSittingSeatsPrice" :1140,
    "nonWindowSleeperSeatsPrice":0,
    "nonWindowSittingSeatsPrice":1010,
    "duration":"17h45min",
    "busDriverNumber":"1234567890",
    "droppingPointRest":[true,false],
    "boardingRestPoint":[false,true],
    "routeAvailability": ["Tuesday","Thrusday","Sunday"],
    "arrivalAndDepartureTime": ["07:37:50","12:37:50"],
    "routeStartingAndEndpoints":["Hyderabad","Delhi"],
    "routeBoardingPoints": ["Hyderabad 2nd ISBT","Hyderabad PVR mall"],
    "routeBoardingLandmarks":["Main Bus Stop Hyderabad","PVR IMAX 4D mall"],
    "routeDroppingPoints": ["Metro Station","Airport"],
    "routeDroppingPointsLandmark":["Chandni Chawk","Too much pollution"],
    "droppingPointsArrivalTime":["15:00:00","18:00:00"],
    "droppingPointsDepartureTime":["15:10:00","18:10:00"],
    "boardingPointsArrivalTime":["09:00:00","09:30:00"],
    "boardingPointsDepartureTime":["09:10:00","09:45:00"]
}*/
