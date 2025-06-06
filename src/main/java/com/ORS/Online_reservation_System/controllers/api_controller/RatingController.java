package com.ORS.Online_reservation_System.controllers.api_controller;

import com.ORS.Online_reservation_System.DTO.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class RatingController {

    @Controller
    @RequestMapping("")
    public static class FrontEndController {
        @GetMapping("/")
        public String home(Model model) {
            return "/customer_dashboard/homepage";
        }

        @GetMapping("/login")
        public String index(Model model) {
            model.addAttribute("loginForm", new LoginDTO() );
            return "/customer_dashboard/registrationpage";
        }

        @GetMapping("/about")
        public String about(Model model) {
            return "/customer_dashboard/about";
        }

        @GetMapping("/hotelDetails")
        public String hotelDetails(Model model) {
            return "/customer_dashboard/hoteldetailpage";
        }

        @GetMapping("/hotelListing")
        public String hotelListingPage(Model model) {
            return "/customer_dashboard/hotellistingpage";
        }

        @GetMapping("/bookingFlow")
        public String bookingFlow(Model model) {
            return "/customer_dashboard/bookingflow";
        }

    }
}
