package com.ORS.Online_reservation_System.Controllers.viewcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/Dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        return "/admin_dashboard/hotel/dashboard";
    }

    @GetMapping("/HotelManagement")
    public String adminHotelManagement(Model model) {
        model.addAttribute("activePage", "dashboard");
        return "/admin_dashboard/hotel/hotelsmanagement";
    }

    @GetMapping("/reservationOverview")
    public String adminReservationOverview(Model model) {
        return "/admin_dashboard/hotel/reservation_overview";
    }

    @GetMapping("/roomManagement")
    public String adminRoomManagement(Model model) {
        return "/admin_dashboard/hotel/system";
    }

    @GetMapping("/system")
    public String adminSystem(Model model) {
        return "/admin_dashboard/hotel/system";
    }

    @GetMapping("/UserManagement")
    public String adminUserManagement(Model model) {
        return "/admin_dashboard/hotel/user_management";
    }

}
