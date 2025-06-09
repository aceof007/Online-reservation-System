package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsernameIgnoreCase(String username);


    @Query("SELECT u FROM User u WHERE " +
            "u.username LIKE %:query% OR u.email LIKE %:query%")
    Page<User> findByUsernameContainingOrEmailContaining(
            @Param("query") String query, Pageable pageable);

//    Page<User> findAll(Specification<User> spec, Pageable pageable);
//    @Query("SELECT u FROM User u WHERE " +
//            "u.username LIKE %:query% OR u.email LIKE %:query%")
//    Page<User> findByUsernameContainingOrEmailContaining(
//            @Param("query") String query, Pageable pageable);
}
