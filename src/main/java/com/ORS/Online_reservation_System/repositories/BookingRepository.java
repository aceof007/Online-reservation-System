package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
    SELECT COUNT(b) > 0 FROM Booking b
    WHERE b.specificRoom.specificRoomId = :specificRoomId
    AND (:checkIn < b.checkOutDate AND :checkOut > b.checkInDate)
""")
    boolean existsBookingForSpecificRoomAndDates(
            @Param("specificRoomId") Long specificRoomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );


}
