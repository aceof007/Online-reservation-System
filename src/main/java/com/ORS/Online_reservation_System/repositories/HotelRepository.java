
// HotelRepository.java
package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Find active hotels
    List<Hotel> findByIsActiveTrue();

    // Find hotels by city
    List<Hotel> findByCityIgnoreCaseAndIsActiveTrue(String city);

    // Find hotels by state
    List<Hotel> findByStateIgnoreCaseAndIsActiveTrue(String state);

    // Find hotels by star rating
    List<Hotel> findByStarRatingAndIsActiveTrue(Integer starRating);

    // Find hotels by star rating greater than or equal
    List<Hotel> findByStarRatingGreaterThanEqualAndIsActiveTrue(Integer starRating);

    // Search hotels by name
    List<Hotel> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    // Find hotel by name (exact match)
    Optional<Hotel> findByNameIgnoreCase(String name);

    @Query("SELECT SUM(r.totalQuantity) FROM Room r WHERE r.hotel.isActive = true")
    long sumAllRooms();

    // Find hotels by city and star rating
    List<Hotel> findByCityIgnoreCaseAndStarRatingAndIsActiveTrue(String city, Integer starRating);

    // Custom query to find hotels with amenities
    @Query("SELECT DISTINCT h FROM Hotel h JOIN h.amenities a WHERE a.name = :amenityName AND h.isActive = true")
    List<Hotel> findHotelsWithAmenity(@Param("amenityName") String amenityName);



    // Find hotels within a geographic area (if coordinates are available)
    @Query("SELECT h FROM Hotel h WHERE h.latitude BETWEEN :minLat AND :maxLat AND h.longitude BETWEEN :minLng AND :maxLng AND h.isActive = true")
    List<Hotel> findHotelsInArea(@Param("minLat") Double minLat, @Param("maxLat") Double maxLat,
                                 @Param("minLng") Double minLng, @Param("maxLng") Double maxLng);

    boolean existsById(Long id);


}
