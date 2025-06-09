package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.repositories.*;
import com.ORS.Online_reservation_System.services.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
// Make sure you have these imports
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              HotelRepository hotelRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Booking> getRecentBookings() {
        return bookingRepository.findRecentBookings();
    }

    @Override
    public Page<Booking> getFilteredBookings(BookingStatus status, Long hotelId,
                                             RoomType roomType, LocalDate startDate,
                                             LocalDate endDate, int page, int size) {
        return bookingRepository.findFilteredBookings(
                status, hotelId, roomType, startDate, endDate,
                PageRequest.of(page, size)
        );
    }

    @Override
    public Booking getBookingDetails(Integer bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
}