// RoomAmenityRepository.java
package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.RoomAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomAmenityRepository extends JpaRepository<RoomAmenity, Long> {

    // Find amenities by room
    List<RoomAmenity> findByRoomId(Long roomId);

    // Find rooms by amenity
    List<RoomAmenity> findByAmenityId(Long amenityId);

    // Check if room has specific amenity
    boolean existsByRoomIdAndAmenityId(Long roomId, Long amenityId);

    // Delete by room and amenity
    void deleteByRoomIdAndAmenityId(Long roomId, Long amenityId);

    // Delete all amenities for a room
    void deleteByRoomId(Long roomId);

    // Find rooms with specific amenities
    @Query("SELECT ra FROM RoomAmenity ra JOIN FETCH ra.amenity WHERE ra.roomId IN :roomIds")
    List<RoomAmenity> findByRoomIdIn(@Param("roomIds") List<Long> roomIds);
}
