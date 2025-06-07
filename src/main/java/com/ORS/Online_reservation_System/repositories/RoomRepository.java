package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Room;
import com.ORS.Online_reservation_System.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotel_HotelId(Long hotelId);

    List<Room> findByHotel_HotelIdAndIsAvailableTrue(Long hotelId);

    List<Room> findByRoomType(RoomType roomType);

    Optional<Room> findByHotel_HotelIdAndRoomNumber(Long hotelId, String roomNumber);

    List<Room> findByCapacityGreaterThanEqual(Integer capacity);

    List<Room> findByPricePerNightBetween(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("SELECT r FROM Room r WHERE r.hotel.hotelId = :hotelId AND r.capacity >= :capacity AND r.isAvailable = true")
    List<Room> findAvailableRoomsByHotelAndCapacity(@Param("hotelId") Long hotelId, @Param("capacity") Integer capacity);

    Long countByHotel_HotelId(Long hotelId);

    Long countByHotel_HotelIdAndIsAvailableTrue(Long hotelId);

    boolean existsByHotel_HotelIdAndRoomNumber(Long hotelId, String roomNumber);

    @Query("SELECT DISTINCT r FROM Room r LEFT JOIN FETCH r.roomAmenities WHERE r.hotel.hotelId = :hotelId")
    List<Room> findRoomsWithAmenitiesByHotel(@Param("hotelId") Long hotelId);
}
