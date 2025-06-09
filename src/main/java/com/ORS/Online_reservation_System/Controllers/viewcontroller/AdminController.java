package com.ORS.Online_reservation_System.controllers.viewcontroller;

import com.ORS.Online_reservation_System.model.*;
import com.ORS.Online_reservation_System.repositories.HotelRepository;
import com.ORS.Online_reservation_System.repositories.UserRepository;
import com.ORS.Online_reservation_System.services.BookingService;
import com.ORS.Online_reservation_System.services.DashboardService;
import com.ORS.Online_reservation_System.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final DashboardService dashboardService;
    private final BookingService bookingService;
    private final HotelRepository hotelRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public AdminController(DashboardService dashboardService, BookingService bookingService, HotelRepository hotelRepository, UserService userService, UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.bookingService = bookingService;
        this.hotelRepository = hotelRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/Dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("activePage", "dashboard");
        model.addAttribute("totalProperties", dashboardService.getTotalProperties());
        model.addAttribute("totalBookings", dashboardService.getTotalBookings());
        model.addAttribute("totalGuests", dashboardService.getTotalGuests());
        model.addAttribute("occupancyRate", dashboardService.getOccupancyRate());

        // Chart data
        model.addAttribute("bookingAnalytics", dashboardService.getBookingAnalytics("weekly"));
        model.addAttribute("revenueByProperty", dashboardService.getRevenueByProperty("month"));
        model.addAttribute("performanceMetrics", dashboardService.getPerformanceMetrics());
        model.addAttribute("bookingSources", dashboardService.getBookingSources());
        model.addAttribute("roomPerformance", dashboardService.getRoomPerformance());

        model.addAttribute("recentBookings", bookingService.getRecentBookings());

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

    @GetMapping("/helpandsupport")
    public String adminRoomManagement(Model model) {
        return "/admin_dashboard/hotel/helpandsupport";
    }

    @GetMapping("/system")
    public String adminSystem(Model model) {
        return "/admin_dashboard/hotel/system";
    }


    @GetMapping("/UserManagement")
    public String adminUserManagement(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String status,
            Model model) {

        Page<User> users;
        if (search != null && !search.isEmpty()) {
            users = userRepository.findByUsernameContainingOrEmailContaining(
                    search, PageRequest.of(page, 10));
        } else {
            users = userRepository.findAll(PageRequest.of(page, 10));
        }

        // Build the dynamic query based on filters
        Specification<User> spec = Specification.where(null);

        if (search != null && !search.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("username")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("email")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("firstName")), "%" + search.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("lastName")), "%" + search.toLowerCase() + "%")
                    )
            );
        }

        if (role != null && !role.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("role"), role)
            );
        }

        if (status != null && !status.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status)
            );
        }

        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("searchQuery", search);
        model.addAttribute("selectedRole", role);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("allRoles", UserRole.values());
        model.addAttribute("allStatuses", UserStatus.values());
        model.addAttribute("activePage", "user_management");


        model.addAttribute("users", users);
        model.addAttribute("searchQuery", search);
        model.addAttribute("roleFilter", role);
        model.addAttribute("statusFilter", status);


        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("searchQuery", search);
        model.addAttribute("activePage", "user_management");

        return "/admin_dashboard/hotel/user_management";
    }

    @PostMapping("/UserManagement/status")
    @ResponseBody
    public String updateUserStatus(
            @RequestParam Long userId,
            @RequestParam String status) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        userRepository.save(user);
        return "Status updated successfully";
    }

    @PostMapping("/UserManagement/delete")
    @ResponseBody
    public String deleteUsers(@RequestBody List<Long> userIds) {
        userRepository.deleteAllById(userIds);
        return "Users deleted successfully";
    }
//    @GetMapping("/UserManagement")
//    public String adminUserManagement(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(required = false) String search,
//            Model model) {
//
//        Page<User> users;
//        if (search != null && !search.isEmpty()) {
//            users = userService.searchUsers(search, page, 10);
//        } else {
//            users = userService.getAllUsers(page, 10);
//        }
//
//        model.addAttribute("users", users);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", users.getTotalPages());
//        model.addAttribute("searchQuery", search);
//        model.addAttribute("activePage", "userManagement");
//
//        return "/admin_dashboard/hotel/user_management";
//    }
//
//    // User Management API endpoints
//    @PostMapping("/users/status")
//    @ResponseBody
//    public String updateUserStatus(
//            @RequestParam String userId,
//            @RequestParam String status) {
//
//        String[] userIdArray = userId.split(",");
//        for (String id : userIdArray) {
//            userService.updateUserStatus(Long.parseLong(id.trim()), UserStatus.valueOf(status));
//        }
//        return "Status updated successfully";
//    }
//
//    @PostMapping("/users/delete")
//    @ResponseBody
//    public String deleteUsers(@RequestBody List<Long> userIds) {
//        userService.deleteUsers(userIds);
//        return "Users deleted successfully";
//    }


    @GetMapping("/Dashboard/chart-data")
    @ResponseBody
    public Map<String, Object> getChartData(
            @RequestParam String chart,
            @RequestParam String period) {

        switch (chart) {
            case "bookingChart":
                return dashboardService.getBookingAnalytics(period);
            case "revenueChart":
                return dashboardService.getRevenueByProperty(period);
            // Add other cases as needed
            default:
                return Collections.emptyMap();
        }
    }


    @GetMapping("/bookingManagement")
    public String bookingManagement(
            @RequestParam(required = false) BookingStatus status,
            @RequestParam(required = false) Long hotelId,
            @RequestParam(required = false) RoomType roomType,
            @RequestParam(required = false) String dateRange,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        // Parse date range
        LocalDate startDate = null;
        LocalDate endDate = null;
        if (dateRange != null) {
            switch (dateRange.toLowerCase()) {
                case "today":
                    startDate = endDate = LocalDate.now();
                    break;
                case "week":
                    startDate = LocalDate.now().with(DayOfWeek.MONDAY);
                    endDate = LocalDate.now();
                    break;
                case "month":
                    startDate = LocalDate.now().withDayOfMonth(1);
                    endDate = LocalDate.now();
                    break;
                case "quarter":
                    LocalDate now = LocalDate.now();
                    int quarter = (now.getMonthValue() - 1) / 3;
                    startDate = LocalDate.of(now.getYear(), quarter * 3 + 1, 1);
                    endDate = now;
                    break;
                case "year":
                    startDate = LocalDate.of(LocalDate.now().getYear(), 1, 1);
                    endDate = LocalDate.now();
                    break;
//                case "custom":
                    // Handle custom date range from request parameters
//                    if (customStart != null && customEnd != null) {
//                        try {
//                            startDate = LocalDate.parse(customStart);
//                            endDate = LocalDate.parse(customEnd);
//                        } catch (DateTimeParseException e) {
//                            // Handle parse error or use default range
//                            startDate = null;
//                            endDate = null;
//                        }
//                    }
//
//                    break;
                default:
                    // No date range filtering
                    break;
            }
        }

        Page<Booking> bookings = bookingService.getFilteredBookings(
                status, hotelId, roomType, startDate, endDate, page, 10);

        model.addAttribute("bookings", bookings);
        model.addAttribute("hotels", hotelRepository.findAll());
        model.addAttribute("statuses", BookingStatus.values());
        model.addAttribute("roomTypes", RoomType.values());

        return "/admin_dashboard/hotel/booking_management";
    }

    @GetMapping("/booking/{id}")
    public String viewBooking(@PathVariable Integer id, Model model) {
        model.addAttribute("booking", bookingService.getBookingDetails(id));
        return "/admin_dashboard/hotel/booking_details";
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("role") UserRole role,
            @RequestParam("status") UserStatus status,
            @RequestParam("password") String password,
            @RequestParam(value = "profilePhoto", required = false) MultipartFile profilePhoto) {

        try {
            // Check if email or username already exists
            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(
                        Map.of("message", "Email already exists"));
            }

            if (userRepository.existsByUsernameIgnoreCase(username)) {
                return ResponseEntity.badRequest().body(
                        Map.of("message", "Username already exists"));
            }

            // Create new user
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setEmail(email);
            user.setRole(role.name());
            user.setStatus(status.name());
            user.setPassword(password);
            user.setRegistrationDate(new Date());

            // Handle profile photo upload
//            if (profilePhoto != null && !profilePhoto.isEmpty()) {
//                String fileName = "user_" + username + "_" + System.currentTimeMillis() +
//                        "." + StringUtils.getFilenameExtension(profilePhoto.getOriginalFilename());
//
//                Path uploadPath = Paths.get("uploads/profile-photos");
//                Files.createDirectories(uploadPath);
//
//                try (InputStream inputStream = profilePhoto.getInputStream()) {
//                    Files.copy(inputStream, uploadPath.resolve(fileName),
//                            StandardCopyOption.REPLACE_EXISTING);
//                    user.setProfilePhotoPath("/uploads/profile-photos/" + fileName);
//                }
//            }

            userRepository.save(user);

            return ResponseEntity.ok(Map.of(
                    "message", "User created successfully",
                    "userId", user.getUserId()
            ));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    Map.of("message", "Error creating user: " + e.getMessage()));
        }
    }

}
