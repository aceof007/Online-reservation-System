package com.ORS.Online_reservation_System.Controllers.api_controller;

import com.ORS.Online_reservation_System.DTO.LoginDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/bookingFlow")
    public String bookingFlow(Model model) {
        return "/customer/bookingflow";
    }

}
