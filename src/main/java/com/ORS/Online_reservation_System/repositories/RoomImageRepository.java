package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.RoomImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {

    List<RoomImage> findByRoom_RoomIdOrderByIsPrimaryDescUploadedDateAsc(Long roomId);

    Optional<RoomImage> findByRoom_RoomIdAndIsPrimaryTrue(Long roomId);

    Long countByRoom_RoomId(Long roomId);

    void deleteByRoom_RoomId(Long roomId);
}
