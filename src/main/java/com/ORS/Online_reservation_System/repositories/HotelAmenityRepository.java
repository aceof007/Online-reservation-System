// HotelAmenityRepository.java
package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.HotelAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelAmenityRepository extends JpaRepository<HotelAmenity, Long> {

    // Find amenities by hotel ID
    List<HotelAmenity> findByHotel_HotelId(Long hotelId);

    // Find hotels that have a specific amenity
    List<HotelAmenity> findByAmenityIgnoreCase(String amenity);

    // Get all unique amenities
    @Query("SELECT DISTINCT ha.amenity FROM HotelAmenity ha ORDER BY ha.amenity")
    List<String> findAllUniqueAmenities();

    // Check if hotel has specific amenity
    boolean existsByHotel_HotelIdAndAmenityIgnoreCase(Long hotelId, String amenity);

    // Delete amenities by hotel ID
    void deleteByHotel_HotelId(Long hotelId);
}
