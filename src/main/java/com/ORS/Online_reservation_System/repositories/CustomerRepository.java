package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Customer;
import com.ORS.Online_reservation_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String Email);
    boolean existsByEmail(String Email);
}
