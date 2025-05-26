package com.ORS.Online_reservation_System.repositories;


import com.ORS.Online_reservation_System.model.Option;
import com.ORS.Online_reservation_System.model.OptionCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer> {

    List<Option> findByAvailabilityTrue();

    List<Option> findByCategory(OptionCategory category);

    List<Option> findByCategoryAndAvailabilityTrue(OptionCategory category);

    List<Option> findByPriceLessThanEqual(Double maxPrice);

    @Query("SELECT o FROM Option o WHERE o.name LIKE %:keyword% OR o.description LIKE %:keyword%")
    List<Option> searchByKeyword(@Param("keyword") String keyword);
}
