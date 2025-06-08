package com.ORS.Online_reservation_System.Controllers.viewcontroller;

import com.ORS.Online_reservation_System.DTO.AvailabilityDTO;
import com.ORS.Online_reservation_System.DTO.LoginDTO;
import com.ORS.Online_reservation_System.model.AmenityCategory;
import com.ORS.Online_reservation_System.serviceimplementation.HotelServiceImpl;
import com.ORS.Online_reservation_System.serviceimplementation.RoomServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class FrontEndController {

    private final HotelServiceImpl hotelService;
    private final RoomServiceImpl roomService;

    public FrontEndController(HotelServiceImpl hotelService, RoomServiceImpl roomService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
    }

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

    @GetMapping("/hotelDetails/{id}")
    public String hotelDetails(Model model, @PathVariable(required = false) Long id) {
        model.addAttribute("hotel", hotelService.getHotelById(id));
        model.addAttribute("AmenityCategory", hotelService.amenityCategories());
        model.addAttribute("availabilityRequest", new AvailabilityDTO());
        return "/customer/hoteldetailpage";
    }

    @GetMapping("/hotelListing")
    public String hotelListingPage(Model model) {
        model.addAttribute("hotels", hotelService.findAllHotels());
        model.addAttribute("roomService", roomService);
        return "/customer/hotellistingpage";
    }
}