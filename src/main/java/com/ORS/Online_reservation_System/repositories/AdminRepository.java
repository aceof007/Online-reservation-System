package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Admin;
import com.ORS.Online_reservation_System.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

}
