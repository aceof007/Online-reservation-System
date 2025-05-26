package com.ORS.Online_reservation_System.controllers;

import com.ORS.Online_reservation_System.dto.ReviewDTO;
import com.ORS.Online_reservation_System.dto.ReviewResponseDTO;
import com.ORS.Online_reservation_System.model.Review;
import com.ORS.Online_reservation_System.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        Review savedReview = reviewService.saveReview(review);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @PostMapping("/submit")
    public ResponseEntity<Review> submitReview(@RequestBody ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        Review submittedReview = reviewService.submitReview(review);
        return new ResponseEntity<>(submittedReview, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Integer id) {
        Optional<Review> review = reviewService.findById(id);
        return review.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.findAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Review>> getReviewsByHotelId(@PathVariable Integer hotelId) {
        List<Review> reviews = reviewService.findReviewsByHotelId(hotelId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUserId(@PathVariable Integer userId) {
        List<Review> reviews = reviewService.findReviewsByUserId(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<Review>> getReviewsByBookingId(@PathVariable Integer bookingId) {
        List<Review> reviews = reviewService.findReviewsByBookingId(bookingId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/approved")
    public ResponseEntity<List<Review>> getApprovedReviewsByHotelId(@PathVariable Integer hotelId) {
        List<Review> reviews = reviewService.findApprovedReviewsByHotelId(hotelId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Review>> getPendingReviews() {
        List<Review> reviews = reviewService.findPendingReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/rating")
    public ResponseEntity<Float> getAverageRatingForHotel(@PathVariable Integer hotelId) {
        Float rating = reviewService.getAverageRatingForHotel(hotelId);
        return new ResponseEntity<>(rating, HttpStatus.OK);
    }

    @GetMapping("/hotel/{hotelId}/count")
    public ResponseEntity<Long> getReviewCountForHotel(@PathVariable Integer hotelId) {
        Long count = reviewService.getReviewCountForHotel(hotelId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(
            @PathVariable Integer id,
            @RequestBody ReviewDTO reviewDTO) {
        Review updatedReview = reviewService.updateReview(id, reviewDTO.getComment(), reviewDTO.getRating());
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable Integer id) {
        boolean deleted = reviewService.deleteReview(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Boolean> approveReview(@PathVariable Integer id) {
        boolean approved = reviewService.approveReview(id);
        return new ResponseEntity<>(approved, HttpStatus.OK);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Boolean> rejectReview(@PathVariable Integer id) {
        boolean rejected = reviewService.rejectReview(id);
        return new ResponseEntity<>(rejected, HttpStatus.OK);
    }

    @PostMapping("/{id}/respond")
    public ResponseEntity<Boolean> respondToReview(
            @PathVariable Integer id,
            @RequestBody ReviewResponseDTO responseDTO) {
        boolean responded = reviewService.respondToReview(id, responseDTO.getResponse());
        return new ResponseEntity<>(responded, HttpStatus.OK);
    }

    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setUserId(reviewDTO.getUserId());
        review.setHotelId(reviewDTO.getHotelId());
        review.setBookingId(reviewDTO.getBookingId());
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        if (reviewDTO.getDate() != null) {
            review.setDate(reviewDTO.getDate());
        }
        if (reviewDTO.getResponse() != null) {
            review.setResponse(reviewDTO.getResponse());
        }
        if (reviewDTO.getStatus() != null) {
            review.setStatus(reviewDTO.getStatus());
        }
        return review;
    }

}
