package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.DTO.BookingDTO;
import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.repositories.*;
import com.ORS.Online_reservation_System.services.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl{

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final SpecificRoomRepository specificRoomRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Transactional
    public Booking createBooking(BookingDTO bookingDTO) {
        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));

        // Find available specific room
        SpecificRoom specificRoom = specificRoomRepository.findAvailableRoom(
                bookingDTO.getRoomId(), bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate()
        ).orElseThrow(() -> new RuntimeException("No available room for selected dates"));

        // Create or fetch customer
        if(!customerRepository.existsByEmail(bookingDTO.getEmail())) {
            Customer customer = new Customer();
            customer.setFirstName(bookingDTO.getFirstName());
            customer.setLastName(bookingDTO.getLastName());
            customer.setRole("CUSTOMER");
            customer.setRegistrationDate(new Date());
            customer.setEmail(bookingDTO.getEmail());
            customer.setPhoneNumber(bookingDTO.getPhone());
            customer.setPassword(bookingDTO.getPassword());
            customer = customerRepository.save(customer);
        }
        Customer customer = customerRepository.findByEmail(bookingDTO.getEmail());
        // Create booking
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setSpecificRoom(specificRoom);
        booking.setCheckInDate(bookingDTO.getCheckInDate());
        booking.setCheckOutDate(bookingDTO.getCheckOutDate());
        booking.setTotalPrice(BigDecimal.valueOf(bookingDTO.getTotalPrice()));
        booking.setStatus(BookingStatus.PENDING); // Enum or constant
        return bookingRepository.save(booking);
    }

    public Optional<SpecificRoom> findAvailableSpecificRoom(Room room, LocalDate checkIn, LocalDate checkOut) {
        List<SpecificRoom> specificRooms = specificRoomRepository.findByRoom(room);

        for (SpecificRoom sr : specificRooms) {
            boolean isBooked = bookingRepository.existsBySpecificRoomAndCheckOutDateAfterAndCheckInDateBefore(
                    sr, checkIn, checkOut
            );

            if (!isBooked && Boolean.TRUE.equals(sr.getIsAvailable())) {
                return Optional.of(sr);
            }
        }

        return Optional.empty();
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }


}

