package com.ORS.Online_reservation_System.controllers;

import com.ORS.Online_reservation_System.DTO.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // Public area mappings
    @GetMapping("/")
    public String home(Model model) {
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
    public String hotelListing() {
        return "public/hotellistingpage";
    }


    @GetMapping("/login")
    public String index(Model model) {
        model.addAttribute("loginForm", new LoginDTO() );
        return "/public/registrationpage";
    }
    @GetMapping("/register")
    public String register() {
        return "public/registrationpage";
    }
}