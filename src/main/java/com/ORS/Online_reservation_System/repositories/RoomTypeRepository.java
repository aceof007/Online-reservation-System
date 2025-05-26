// RoomTypeRepository.java
package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    // Find active room types
    List<RoomType> findByIsActiveTrueOrderByTypeName();

    // Find by type name
    Optional<RoomType> findByTypeName(String typeName);

    // Check if type name exists
    boolean existsByTypeName(String typeName);
}