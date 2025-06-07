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
        return "/customer/homepage";
    }

    @GetMapping("/login")
    public String index(Model model) {
        model.addAttribute("loginForm", new LoginDTO() );
        return "/customer/registrationpage";
    }

    @GetMapping("/about")
    public String about(Model model) {
        return "/customer/about";
    }

    @GetMapping("/hotelDetails")
    public String hotelDetails(Model model) {
        return "/customer/hoteldetailpage";
    }

    @GetMapping("/hotelListing")
    public String hotelListingPage(Model model) {
        return "/customer/hotellistingpage";
    }
}