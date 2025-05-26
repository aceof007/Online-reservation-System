// RoomImageRepository.java
package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {

    // Find images by room
    List<RoomImage> findByRoomIdOrderByIsPrimaryDescUploadedDateAsc(Long roomId);

    // Find primary image for room
    Optional<RoomImage> findByRoomIdAndIsPrimaryTrue(Long roomId);

    // Count images for room
    Long countByRoomId(Long roomId);

    // Delete all images for room
    void deleteByRoomId(Long roomId);
}
