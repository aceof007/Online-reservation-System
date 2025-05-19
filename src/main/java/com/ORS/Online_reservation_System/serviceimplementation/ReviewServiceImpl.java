package com.ORS.Online_reservation_System.serviceimplementation;

//import javax.persistence.EntityNotFoundException;
import com.ORS.Online_reservation_System.model.Review;
import com.ORS.Online_reservation_System.model.ReviewStatus;
import com.ORS.Online_reservation_System.repositories.ReviewRepository;
import com.ORS.Online_reservation_System.services.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findReviewsByHotelId(Integer hotelId) {
        return reviewRepository.findByHotelId(hotelId);
    }

    @Override
    public List<Review> findReviewsByUserId(Integer userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public List<Review> findReviewsByBookingId(Integer bookingId) {
        return reviewRepository.findByBookingId(bookingId);
    }

    @Override
    public List<Review> findApprovedReviewsByHotelId(Integer hotelId) {
        return reviewRepository.findByHotelIdAndStatus(hotelId, ReviewStatus.APPROVED);
    }

    @Override
    public List<Review> findPendingReviews() {
        return reviewRepository.findPendingReviewsSortedByDate();
    }

    @Override
    public Float getAverageRatingForHotel(Integer hotelId) {
        return reviewRepository.calculateAverageRatingForHotel(hotelId);
    }

    @Override
    public Long getReviewCountForHotel(Integer hotelId) {
        return reviewRepository.countApprovedReviewsForHotel(hotelId);
    }

    @Override
    public Review submitReview(Review review) {
        return reviewRepository.save(review.submitReview());
    }

    @Override
    public Review updateReview(Integer id, String comment, Float rating) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));

        review.updateReview(comment, rating);
        return reviewRepository.save(review);
    }

    @Override
    public boolean deleteReview(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));

        boolean deleted = review.deleteReview();
        if (deleted) {
            reviewRepository.save(review);
        }
        return deleted;
    }

    @Override
    public boolean approveReview(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));

        boolean approved = review.approveReview();
        if (approved) {
            reviewRepository.save(review);
        }
        return approved;
    }

    @Override
    public boolean rejectReview(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));

        review.setStatus(ReviewStatus.REJECTED);
        reviewRepository.save(review);
        return true;
    }

    @Override
    public boolean respondToReview(Integer id, String response) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + id));

        boolean responded = review.respondToReview(response);
        if (responded) {
            reviewRepository.save(review);
        }
        return responded;
    }
}
