package com.redbus.backend_redbus.service;

import com.redbus.backend_redbus.model.Coupon;
import com.redbus.backend_redbus.repository.CouponRepository;
import com.redbus.backend_redbus.request.and.responses.CouponRequest;
import com.redbus.backend_redbus.request.and.responses.NewCouponRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public boolean isValidCoupon(CouponRequest couponRequest)
    {
        String code = couponRequest.getCouponCode();
        Coupon coupon = couponRepository.findByCouponCodeIgnoreCase(code);
        if(coupon==null)
            return false;
        return true;
    }
    public Coupon getCouponDetails(CouponRequest couponRequest)
    {
        String code = couponRequest.getCouponCode();
        Coupon coupon = couponRepository.findByCouponCodeIgnoreCase(code);
        return coupon;
    }
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
    public void addCouponManually() throws ParseException {
        Coupon coupon = new Coupon();
        coupon.setCouponName("Diwali Offer");
        coupon.setOffPercent(25);
        coupon.setMaxOffPrice(200);
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse("2020-01-25");
        coupon.setExpiryTime(date);
        coupon.setCouponCode("DIWALI786");
        couponRepository.save(coupon);
        Coupon coupon1 = new Coupon();
        coupon1.setCouponName("Your First Journey");
        coupon1.setOffPercent(70);
        coupon1.setMaxOffPrice(200);
        Date date1 = new SimpleDateFormat("yyyy-mm-dd").parse("2020-01-25");
        coupon1.setExpiryTime(date1);
        coupon1.setCouponCode("FIRSTBUS");
        couponRepository.save(coupon1);
        Coupon coupon2 = new Coupon();
        coupon2.setCouponName("Lucky Offer");
        coupon2.setOffPercent(50);
        coupon2.setMaxOffPrice(100);
        Date date2 = new SimpleDateFormat("yyyy-mm-dd").parse("2020-01-25");
        coupon2.setExpiryTime(date2);
        coupon2.setCouponCode("LUCKYMAN");
        couponRepository.save(coupon2);

    }
    public void addCoupon(NewCouponRequest newCouponRequest) throws ParseException {
        Coupon coupon = new Coupon();
        coupon.setCouponCode(newCouponRequest.getCouponCode());
        Date date = new SimpleDateFormat("yyyy-mm-dd").parse(newCouponRequest.getExpirationDate());
        coupon.setExpiryTime(date);
        coupon.setMaxOffPrice(newCouponRequest.getMaxOffPrice());
        coupon.setOffPercent(newCouponRequest.getOffPercent());
        coupon.setCouponName(newCouponRequest.getCouponName());
        couponRepository.save(coupon);
    }

}
