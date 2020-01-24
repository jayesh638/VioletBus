package com.redbus.backend_redbus.repository;

import com.redbus.backend_redbus.model.UserRatingReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRatingRepository  extends JpaRepository<UserRatingReview,Long> {
}
