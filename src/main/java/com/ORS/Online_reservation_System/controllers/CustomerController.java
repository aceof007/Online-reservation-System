package com.ORS.Online_reservation_System.Controllers;

import com.ORS.Online_reservation_System.DTO.LoginDTO;
import com.ORS.Online_reservation_System.model.Customer;
import com.ORS.Online_reservation_System.serviceimplementation.CustomerServiceImpl;
import com.ORS.Online_reservation_System.serviceimplementation.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;
    private final UserServiceImpl userService;

    public CustomerController(CustomerServiceImpl customerService, UserServiceImpl userService) {
        this.customerService = customerService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("siteForm") LoginDTO formData,
                          BindingResult result,
                          RedirectAttributes redirectAttrs) {
        // Check if username already exists
        if (userService.existsByEmail(formData.getEmail())) {
            redirectAttrs.addFlashAttribute("error", "Email is already signed up here");
            return "redirect:/login"; // redirect back to registration page
        }

        // Create new Customer
        Customer customer = new Customer();
        Date date = new Date();
        customer.setEmail(formData.getEmail());
        customer.setPassword(formData.getPassword());
        customer.setFirstName(formData.getFirstName());
        customer.setLastName(formData.getLastName());
        customer.setUsername(formData.getUsername());
        customer.setPhoneNumber(formData.getCountryCode() + formData.getPhoneNumber());
        customer.setRegistrationDate(date);
        customer.setRole("CUSTOMER");
        customer.setStatus("ACTIVE");

        customerService.addCustomer(customer);
        System.out.println("Customer added");

        return "redirect:/login";
    }


    @GetMapping("/bookingFlow")
    public String bookingFlow(Model model) {
        return "/customer/bookingflow";
    }

    @GetMapping("/currentReservations")
    public String currentReservations(Model model) {
        return "/signincustomer/currentreservation";
    }

    @GetMapping("/modifyReservation")
    public String modifyReservations(Model model) {
        return "/signincustomer/modifyreservation";
    }

    @GetMapping("/ProfileManegement")
    public String profileManagement(Model model) {
        return "/signincustomer/profilmanagement";
    }

    @GetMapping("/reservationHistory")
    public String reservationHistory(Model model) {
        return "/signincustomer/reservationhistory";
    }

    @GetMapping("/reviewManagement")
    public String reviewManagement(Model model) {
        return "/signincustomer/reviewmanagement";
    }

}
