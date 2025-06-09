package com.ORS.Online_reservation_System.controllers.api_controller;

import com.ORS.Online_reservation_System.DTO.BookingDTO;
import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.repositories.BookingRepository;
import com.ORS.Online_reservation_System.repositories.CustomerRepository;
import com.ORS.Online_reservation_System.repositories.PaymentRepository;
import com.ORS.Online_reservation_System.repositories.RoomRepository;
import com.ORS.Online_reservation_System.serviceimplementation.BookingServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/booking")
public class BookingController {

    BookingServiceImpl bookingService;
    BookingRepository bookingRepository;
    RoomRepository roomRepository;
    CustomerRepository customerRepository;
    PaymentRepository paymentRepository;

    @PostMapping("/submit/{roomId}/{checkIn}/{checkOut}")
    public String submitBooking(@ModelAttribute BookingDTO bookingDTO,
                                RedirectAttributes redirectAttributes,
                                @PathVariable Long roomId, @PathVariable LocalDate checkIn, @PathVariable LocalDate checkOut) {

        System.out.println("bookingDTO: " + bookingDTO);

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room ID"));

        Optional<SpecificRoom> optional = bookingService.findAvailableSpecificRoom(
                room, bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate()
        );

        if (optional.isPresent()) {
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
            booking.setSpecificRoom(optional.get());
            booking.setCheckInDate(checkIn);
            booking.setCheckOutDate(checkOut);
            booking.setTotalPrice(BigDecimal.valueOf(bookingDTO.getTotalPrice()));
            booking.setStatus(BookingStatus.PENDING); // Enum or constant

            bookingService.saveBooking(booking);

            // Now generate payment
            Payment payment = new Payment();
            payment.setBooking(booking);
            payment.setAmount(bookingDTO.getTotalPrice()); // Or total if needed
            payment.setPaymentDate(new Date());
            payment.setPaymentMethod(PaymentMethod.CREDIT_CARD); // or from form
            payment.processPayment(); // This sets status and generates transactionId

            paymentRepository.save(payment);

            return "redirect:/booking/confirmation";
        } else {
            redirectAttributes.addFlashAttribute("error", "No available rooms for the selected type and dates.");
            return "redirect:/booking/form";
        }
    }


}
