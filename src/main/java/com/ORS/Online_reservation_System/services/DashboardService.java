package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.repositories.BookingRepository;
import com.ORS.Online_reservation_System.repositories.HotelRepository;
import com.ORS.Online_reservation_System.repositories.UserRepository;
import com.ORS.Online_reservation_System.services.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(HotelRepository hotelRepository,
                                BookingRepository bookingRepository,
                                UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long getTotalProperties() {
        return hotelRepository.count();
    }

    @Override
    public long getTotalBookings() {
        return bookingRepository.count();
    }

    @Override
    public long getTotalGuests() {
        return userRepository.count(); // Or use customerRepository if you have one
    }

    @Override
    public double getOccupancyRate() {
        long totalRooms = hotelRepository.sumAllRooms(); // You'll need to add this method
        long occupiedRooms = bookingRepository.countActiveBookings(LocalDate.now()); // Add this method
        return totalRooms > 0 ? (occupiedRooms * 100.0) / totalRooms : 0;
    }
}