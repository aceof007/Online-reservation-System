package com.ORS.Online_reservation_System.services;

import com.ORS.Online_reservation_System.model.Booking;
import com.ORS.Online_reservation_System.model.BookingStatus;
import com.ORS.Online_reservation_System.model.RoomType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    List<Booking> getRecentBookings();
    Page<Booking> getFilteredBookings(BookingStatus status, Long hotelId,
                                      RoomType roomType, LocalDate startDate,
                                      LocalDate endDate, int page, int size);
    Booking getBookingDetails(Integer bookingId);
}