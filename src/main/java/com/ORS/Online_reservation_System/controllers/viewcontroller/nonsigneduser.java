package com.ORS.Online_reservation_System.controllers.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class nonsigneduser {

    @GetMapping("/customerdash/about")
    public String about() {

        return "customer_dashboard/about";
    }

    @GetMapping("/customerdash/bookingflow")
    public String bookingflow() {

        return "customer_dashboard/bookingflow";
    }

    @GetMapping("/customerdash/destination")
    public String destination() {

        return "customer_dashboard/destination";
    }

    @GetMapping("/customerdash/homepage")
    public String homepage() {

        return "customer_dashboard/homepage";
    }

    @GetMapping("/customerdash/hoteldetailpage")
    public String hoteldetailpage() {

        return "customer_dashboard/hoteldetailpage";
    }

    @GetMapping("/customerdash/hotellistingpage")
    public String hotellistingpage() {

        return "customer_dashboard/hotellistingpage";
    }

    @GetMapping("/customerdash/registrationpage")
    public String registrationpage() {

        return "customer_dashboard/registrationpage";
    }
}
