package com.ORS.Online_reservation_System.Controllers;

import com.ORS.Online_reservation_System.DTO.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class FrontEndController {
    @GetMapping("/")
    public String home(Model model) {
        return "/public_area/homepage";
    }

    @GetMapping("/login")
    public String index(Model model) {
        model.addAttribute("loginForm", new LoginDTO() );
        return "/public_area/registrationpage";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "/public_area/about";
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