package com.ORS.Online_reservation_System.services;

import com.ORS.Online_reservation_System.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    Review saveReview(Review review);

    Optional<Review> findById(Integer id);

    List<Review> findAllReviews();

    List<Review> findReviewsByHotelId(Integer hotelId);

    List<Review> findReviewsByUserId(Integer userId);

    List<Review> findReviewsByBookingId(Integer bookingId);

    List<Review> findApprovedReviewsByHotelId(Integer hotelId);

    List<Review> findPendingReviews();

    Float getAverageRatingForHotel(Integer hotelId);

    Long getReviewCountForHotel(Integer hotelId);

    Review submitReview(Review review);

    Review updateReview(Integer id, String comment, Float rating);

    boolean deleteReview(Integer id);

    boolean approveReview(Integer id);

    boolean rejectReview(Integer id);

    boolean respondToReview(Integer id, String response);
}