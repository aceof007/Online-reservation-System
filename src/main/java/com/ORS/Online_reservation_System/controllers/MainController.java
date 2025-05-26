package com.ORS.Online_reservation_System.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Public area mappings
    @GetMapping("/")
    public String home() {
        return "public/homepage";
    }

    @GetMapping("/about")
    public String about() {
        return "public/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "public/contact";
    }

    @GetMapping("/hotel")
    public String hotel() {
        return "public/hoteldetailpage";
    }

    @GetMapping("/hotellisting")
    public String hotellisting() {
        return "public/hotellistingpage";
    }


    @GetMapping("/login")
    public String login() {
        return "public/login";
    }

    @GetMapping("/register")
    public String register() {
        return "public/registrationpage";
    }
}