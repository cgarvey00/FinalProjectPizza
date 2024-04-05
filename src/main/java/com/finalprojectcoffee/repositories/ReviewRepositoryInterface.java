package com.finalprojectcoffee.repositories;

import com.finalprojectcoffee.entities.Review;

import java.util.List;

public interface ReviewRepositoryInterface {
    boolean addReview(Review review, int stars);
    List<Review> getAllReviews();
    List<Review> getReviewsByUserId(int userId);
    boolean removeReview(int reviewId);
}
