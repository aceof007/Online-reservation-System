package com.ORS.Online_reservation_System.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservations")
public class BookingController {

    @GetMapping("/new")
    public String newReservationForm() {
        return "customer/new-reservation";
    }

    @PostMapping("/new")
    public String createReservation() {
        // Here you would add code to process the form submission
        // For now, we'll just redirect to a confirmation page
        return "redirect:/reservations/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmationPage() {
        return "customer/reservation-confirmation";
    }

    @GetMapping("/details/{id}")
    public String reservationDetails() {
        return "customer/reservation-details";
    }
}