package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    Coupon findByCouponCodeIgnoreCase(String couponCode);
}
