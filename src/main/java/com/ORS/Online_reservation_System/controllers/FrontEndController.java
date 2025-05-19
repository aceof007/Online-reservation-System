package com.ORS.Online_reservation_System.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("")
public class FrontEndController {
    @GetMapping("/")
    public String home(Model model) {
        return "/public_area/homepage";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "/public_area/about";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "/public_area/registrationpae";
    }

    @GetMapping("/hotelDetails")
    public String hotelDetails(Model model) {
        return "/public_area/hoteldetailpage";
    }

    @GetMapping("/hotelListing")
    public String hotelListingPage(Model model) {
        return "/public_area/hotellistingpage";
    }

    @GetMapping("/bookingFlow")
    public String bookingFlow(Model model) {
        return "/public_area/bookingflow";
    }
}