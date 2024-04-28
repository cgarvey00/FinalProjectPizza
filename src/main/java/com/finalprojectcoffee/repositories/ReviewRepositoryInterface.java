package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Review;
import java.util.Date;
import java.util.List;

public interface ReviewRepositoryInterface {
    boolean addReview(int customer_id, String comment, Date commentDate, int stars);
    List<Review> getAllReviews();
    List<Review> getReviewsByUserId(int userId);
    boolean removeReview(int reviewId);
}
