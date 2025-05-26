
// RoomRepository.java
package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find rooms by hotel
    List<Room> findByHotelId(Long hotelId);

    // Find available rooms by hotel
    List<Room> findByHotelIdAndIsAvailableTrue(Long hotelId);

    // Find rooms by room type
    List<Room> findByRoomTypeId(Long roomTypeId);

    // Find room by hotel and room number
    Optional<Room> findByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

    // Find rooms by capacity
    List<Room> findByCapacityGreaterThanEqual(Integer capacity);

    // Find rooms by price range
    List<Room> findByPricePerNightBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Find available rooms by hotel and capacity
    @Query("SELECT r FROM Room r WHERE r.hotelId = :hotelId AND r.capacity >= :capacity AND r.isAvailable = true")
    List<Room> findAvailableRoomsByHotelAndCapacity(@Param("hotelId") Long hotelId, @Param("capacity") Integer capacity);

    // Count rooms by hotel
    Long countByHotelId(Long hotelId);

    // Count available rooms by hotel
    Long countByHotelIdAndIsAvailableTrue(Long hotelId);

    // Check if room number exists for hotel
    boolean existsByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

    // Find rooms with amenities
    @Query("SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.roomAmenities ra LEFT JOIN FETCH ra.amenity WHERE r.hotelId = :hotelId")
    List<Room> findRoomsWithAmenitiesByHotel(@Param("hotelId") Long hotelId);
}