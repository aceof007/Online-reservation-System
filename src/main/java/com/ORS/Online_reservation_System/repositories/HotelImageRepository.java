// HotelImageRepository.java
package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, Long> {

    // Find images by hotel ID
    List<HotelImage> findByHotel_HotelId(Long hotelId);

    // Find primary image for hotel
    Optional<HotelImage> findByHotel_HotelIdAndIsPrimaryTrue(Long hotelId);

    // Find all primary images
    List<HotelImage> findByIsPrimaryTrue();

    // Delete images by hotel ID
    void deleteByHotel_HotelId(Long hotelId);

    // Count images for a hotel
    long countByHotel_HotelId(Long hotelId);
}
