package com.ORS.Online_reservation_System.Controllers.viewcontroller;

import com.ORS.Online_reservation_System.DTO.BookingDTO;
import com.ORS.Online_reservation_System.DTO.LoginDTO;
import com.ORS.Online_reservation_System.model.Customer;
import com.ORS.Online_reservation_System.serviceimplementation.CustomerServiceImpl;
import com.ORS.Online_reservation_System.serviceimplementation.HotelServiceImpl;
import com.ORS.Online_reservation_System.serviceimplementation.RoomServiceImpl;
import com.ORS.Online_reservation_System.serviceimplementation.UserServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerServiceImpl customerService;
    private final UserServiceImpl userService;
    private final RoomServiceImpl roomService;
    private final HotelServiceImpl hotelService;

    public CustomerController(CustomerServiceImpl customerService, UserServiceImpl userService, RoomServiceImpl roomService, HotelServiceImpl hotelService) {
        this.customerService = customerService;
        this.userService = userService;
        this.roomService = roomService;
        this.hotelService = hotelService;
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
    public String bookingFlow(Model model, @RequestParam Long roomId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
                              Principal principal) {
        model.addAttribute("room", roomService.getRoomById(roomId));
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("roomService", roomService);
        model.addAttribute("bookingForm", new BookingDTO());

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
