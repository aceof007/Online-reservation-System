package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Booking;
import com.ORS.Online_reservation_System.model.BookingStatus;
import com.ORS.Online_reservation_System.model.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("SELECT COUNT(b) FROM Booking b WHERE :date BETWEEN b.checkInDate AND b.checkOutDate")
    long countActiveBookings(@Param("date") LocalDate date);

    @Query("""
    SELECT COUNT(b) > 0 FROM Booking b
    WHERE b.specificRoom.specificRoomId = :specificRoomId
    AND (:checkIn < b.checkOutDate AND :checkOut > b.checkInDate)
""")
    boolean existsBookingForSpecificRoomAndDates(
            @Param("specificRoomId") Long specificRoomId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    );

//    @Query("SELECT TO_CHAR(b.createdAt, 'Mon') as month, COUNT(b) as count " +
//            "FROM Booking b " +
//            "WHERE b.createdAt BETWEEN :startDate AND :endDate " +
//            "GROUP BY TO_CHAR(b.createdAt, 'Mon') " +
//            "ORDER BY MIN(b.createdAt)")
//    List<Object[]> countBookingsByMonth(@Param("startDate") LocalDate startDate,
//                                        @Param("endDate") LocalDate endDate);

//    @Query("SELECT TO_CHAR(b.createdAt, 'Mon') as month, COUNT(b) as count " +
//            "FROM Booking b " +
//            "WHERE b.createdAt BETWEEN :startDate AND :endDate " +
//            "GROUP BY TO_CHAR(b.createdAt, 'Mon') " +
//            "ORDER BY MIN(b.createdAt)")
//    List<Object[]> countBookingsByMonth(@Param("startDate") LocalDateTime startDate,
//                                        @Param("endDate") LocalDateTime endDate);

    @Query("SELECT FUNCTION('MONTH', b.createdAt) as month, COUNT(b) as count " +
            "FROM Booking b " +
            "WHERE b.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('MONTH', b.createdAt) " +
            "ORDER BY FUNCTION('MONTH', b.createdAt)")
    List<Object[]> countBookingsByMonth(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);




    @Query("SELECT h.name, SUM(b.totalPrice) as revenue " +
            "FROM Booking b JOIN b.specificRoom.room.hotel h " +
            "WHERE b.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY h.name " +
            "ORDER BY revenue DESC")
    List<Object[]> sumRevenueByHotel(@Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

//    @Query("SELECT h.name, SUM(b.totalPrice) as revenue " +
//            "FROM Booking b JOIN b.specificRoom.room.hotel h " +
//            "WHERE b.createdAt BETWEEN :startDate AND :endDate " +
//            "GROUP BY h.name " +
//            "ORDER BY revenue DESC")
//    List<Object[]> sumRevenueByHotel(@Param("period") String period);


    @Query("SELECT b FROM Booking b ORDER BY b.createdAt DESC LIMIT 5")
    List<Booking> findRecentBookings();

    @Query("SELECT b FROM Booking b WHERE " +
            "(:status IS NULL OR b.status = :status) AND " +
            "(:hotelId IS NULL OR b.specificRoom.room.hotel.hotelId = :hotelId) AND " +
            "(:roomType IS NULL OR b.specificRoom.room.roomName = :roomType) AND " +
            "(:startDate IS NULL OR b.checkInDate >= :startDate) AND " +
            "(:endDate IS NULL OR b.checkOutDate <= :endDate) " +
            "ORDER BY b.createdAt DESC")

    Page<Booking> findFilteredBookings(
            @Param("status") BookingStatus status,
            @Param("hotelId") Long hotelId,
            @Param("roomType") RoomType roomType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
}
