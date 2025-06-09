package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Room;
import com.ORS.Online_reservation_System.model.SpecificRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpecificRoomRepository extends JpaRepository<SpecificRoom, Integer> {
    @Query("""
    SELECT sr FROM SpecificRoom sr
    WHERE sr.room.roomId = :roomId
      AND sr.room.roomId NOT IN (
        SELECT b.specificRoom.specificRoomId FROM Booking b
        WHERE :checkInDate < b.checkOutDate AND :checkOutDate > b.checkInDate
      )
""")
    Optional<SpecificRoom> findAvailableRoom(@Param("roomId") Long roomId,
                                             @Param("checkInDate") LocalDate checkIn,
                                             @Param("checkOutDate") LocalDate checkOut);

        List<SpecificRoom> findByRoom(Room room);

}
