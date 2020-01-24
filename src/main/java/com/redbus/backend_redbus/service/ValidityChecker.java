package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.request.and.responses.EditBusRequest;
import com.redbus.backend_redbus.request.and.responses.RequestBusReview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ValidityChecker {
    private Logger logger = LoggerFactory.getLogger(ValidityChecker.class);
    boolean checkValidity(List<String> arrivalAndDepartureTime, List<String> routeStartingAndEndpoints, List<String> routeBoardingPoints, List<String> routeDroppingPoints, List<String> routeDroppingPointsLandmark, List<String> routeAvailability, List<String> routeBoardingLandmarks, List<String> droppingPointsDepartureTime, List<String> boardingPointsDepartureTime, List<String> boardingPointsArrivalTime, List<String> droppingPointsArrivalTime, String duration, List<Boolean> boardingRestPoint, List<Boolean> droppingPointRest) {
        logger.info("Now Checking Validity");
        if (arrivalAndDepartureTime == null || routeStartingAndEndpoints == null
                || routeBoardingPoints == null || routeDroppingPoints == null || routeDroppingPointsLandmark == null
                || routeAvailability == null || routeBoardingLandmarks == null || droppingPointsDepartureTime == null
                || boardingPointsDepartureTime == null || boardingPointsArrivalTime == null
                || droppingPointsArrivalTime == null || duration == null
                || arrivalAndDepartureTime.size() != 2 || routeStartingAndEndpoints.size() != 2
                || routeBoardingPoints.size()!=routeBoardingLandmarks.size()
                || routeBoardingPoints.size() != boardingPointsArrivalTime.size()
                || routeBoardingPoints.size() != boardingPointsDepartureTime.size()
                || routeBoardingPoints.size() != boardingRestPoint.size()
                || routeDroppingPoints.size() != routeDroppingPointsLandmark.size()
                || routeDroppingPoints.size() != droppingPointRest.size()
                || routeDroppingPoints.size() != droppingPointsArrivalTime.size()
                || routeDroppingPoints.size() != droppingPointsDepartureTime.size()
        ) {
            logger.warn("Incomplete Data is formed by the user");
            return true;
        }
        return false;
    }
    boolean checkEditBusValidity(EditBusRequest editBusRequest)
    {
        return editBusRequest.getBusDriverName() == null || editBusRequest.getBusDriverNumber() == null ||
                editBusRequest.getBusName() == null;
    }

    boolean checkReviewValidity(RequestBusReview requestBusReview)
    {
        return requestBusReview.getUserReview() == null || requestBusReview.getBusId() == 0 || requestBusReview.getUserId()==null;
    }
}
