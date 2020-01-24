package com.redbus.backend_redbus.database.service;

import com.redbus.backend_redbus.database.interfaces.UserRatingInterface;
import com.redbus.backend_redbus.model.UserRatingReview;
import com.redbus.backend_redbus.repository.UserRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRatingService implements UserRatingInterface {
    @Autowired
    private UserRatingRepository userRatingRepository;

    @Override
    public void saveRating(UserRatingReview userRatingReview) {
        userRatingRepository.save(userRatingReview);
    }
}