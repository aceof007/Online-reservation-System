
// AmenityRepository.java
package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    // Find active amenities
    List<Amenity> findByIsActiveTrueOrderByName();

    // Find by name
    Optional<Amenity> findByName(String name);

    // Check if name exists
    boolean existsByName(String name);

    // Search amenities
    List<Amenity> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    Optional<Amenity> findByNameIgnoreCase(String name);

    @Query("SELECT DISTINCT a.name FROM Amenity a")
    List<String> findAllDistinctNames();

    boolean existsByNameIgnoreCase(String name);


}