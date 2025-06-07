package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Review;
import com.ORS.Online_reservation_System.model.ReviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByHotelId(Integer hotelId);

    List<Review> findByUserId(Integer userId);

    List<Review> findByBookingId(Integer bookingId);

    List<Review> findByHotelIdAndStatus(Integer hotelId, ReviewStatus status);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.hotelId = :hotelId AND r.status = 'APPROVED'")
    Float calculateAverageRatingForHotel(@Param("hotelId") Integer hotelId);

    @Query("SELECT r FROM Review r WHERE r.status = 'PENDING' ORDER BY r.date ASC")
    List<Review> findPendingReviewsSortedByDate();

    @Query("SELECT COUNT(r) FROM Review r WHERE r.hotelId = :hotelId AND r.status = 'APPROVED'")
    Long countApprovedReviewsForHotel(@Param("hotelId") Integer hotelId);
}
