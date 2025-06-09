package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.repositories.BookingRepository;
import com.ORS.Online_reservation_System.repositories.HotelRepository;
import com.ORS.Online_reservation_System.repositories.UserRepository;
import com.ORS.Online_reservation_System.services.DashboardService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Locale;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final HotelRepository hotelRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public DashboardServiceImpl(HotelRepository hotelRepository,
                                BookingRepository bookingRepository,
                                UserRepository userRepository) {
        this.hotelRepository = hotelRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    @Override
    public long getTotalProperties() {
        return hotelRepository.count();
    }

    @Override
    public long getTotalBookings() {
        return bookingRepository.count();
    }

    @Override
    public long getTotalGuests() {
        return userRepository.count(); // Or use customerRepository if you have one
    }

    @Override
    public double getOccupancyRate() {
        long totalRooms = hotelRepository.sumAllRooms(); // You'll need to add this method
        long occupiedRooms = bookingRepository.countActiveBookings(LocalDate.now()); // Add this method
        return totalRooms > 0 ? (occupiedRooms * 100.0) / totalRooms : 0;
    }


    @Override
    public Map<String, Object> getBookingAnalytics(String period) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(6); // Default to 6 months

        if ("weekly".equalsIgnoreCase(period)) {
            startDate = endDate.minusWeeks(1);
        } else if ("monthly".equalsIgnoreCase(period)) {
            startDate = endDate.minusMonths(1);
        } else if ("yearly".equalsIgnoreCase(period)) {
            startDate = endDate.minusYears(1);
        }

        // Convert LocalDate to LocalDateTime at start and end of day
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        Map<String, Long> bookingsData = bookingRepository
                .countBookingsByMonth(startDateTime, endDateTime)
                .stream()
                .collect(Collectors.toMap(
                        obj -> ((Object[]) obj)[0].toString(), // Month name
                        obj -> (Long) ((Object[]) obj)[1]      // Booking count
                ));

        return Map.of(
                "labels", getMonthLabels(startDate, endDate),
                "data", getDataForMonths(bookingsData, startDate, endDate)
        );
    }

//    public Map<String, Object> getBookingAnalytics(String period) {
//        LocalDate endDate = LocalDate.now();
//        LocalDate startDate = endDate.minusMonths(6); // Default to 6 months
//
//        if ("weekly".equalsIgnoreCase(period)) {
//            startDate = endDate.minusWeeks(1);
//        } else if ("monthly".equalsIgnoreCase(period)) {
//            startDate = endDate.minusMonths(1);
//        } else if ("yearly".equalsIgnoreCase(period)) {
//            startDate = endDate.minusYears(1);
//        }
//
//        Map<String, Long> bookingsData = bookingRepository
//                .countBookingsByMonth(startDate, endDate)
//                .stream()
//                .collect(Collectors.toMap(
//                        obj -> ((Object[])obj)[0].toString(), // Month name
//                        obj -> (Long)((Object[])obj)[1]      // Booking count
//                ));
//
//        return Map.of(
//                "labels", getMonthLabels(startDate, endDate),
//                "data", getDataForMonths(bookingsData, startDate, endDate)
//        );
//    }

    private LocalDateTime[] getStartAndEndDates(String period) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(6); // default

        if ("weekly".equalsIgnoreCase(period)) {
            startDate = endDate.minusWeeks(1);
        } else if ("monthly".equalsIgnoreCase(period)) {
            startDate = endDate.minusMonths(1);
        } else if ("yearly".equalsIgnoreCase(period)) {
            startDate = endDate.minusYears(1);
        }
        return new LocalDateTime[] { startDate.atStartOfDay(), endDate.atTime(23, 59, 59) };
    }


    @Override
    public Map<String, Object> getRevenueByProperty(String period) {
        LocalDateTime[] range = getStartAndEndDates(period);
        List<Object[]> revenueData = bookingRepository.sumRevenueByHotel(range[0], range[1]);

        List<String> labels = revenueData.stream()
                .map(obj -> obj[0].toString()) // Hotel name
                .collect(Collectors.toList());

        List<Double> data = revenueData.stream()
                .map(obj -> (Double) obj[1]) // Revenue
                .collect(Collectors.toList());

        return Map.of(
                "labels", labels,
                "data", data
        );
    }

//    public Map<String, Object> getRevenueByProperty(String period) {
//        List<Object[]> revenueData = bookingRepository.sumRevenueByHotel(period);
//
//        List<String> labels = revenueData.stream()
//                .map(obj -> obj[0].toString()) // Hotel name
//                .collect(Collectors.toList());
//
//        List<Double> data = revenueData.stream()
//                .map(obj -> (Double)obj[1]) // Revenue
//                .collect(Collectors.toList());
//
//        return Map.of(
//                "labels", labels,
//                "data", data
//        );
//    }

    @Override
    public Map<String, Object> getPerformanceMetrics() {
        // Implement similar logic for performance metrics
        // Using bookingRepository and roomRepository
        return Map.of(
                "labels", List.of("Week 1", "Week 2", "Week 3", "Week 4"),
                "occupancyData", List.of(65, 59, 80, 81),
                "revenueData", List.of(45, 40, 65, 70)
        );
    }

    @Override
    public Map<String, Object> getBookingSources() {
        // Implement logic to get booking sources
        return Map.of(
                "labels", List.of("Website", "Travel Agency", "Phone", "Walk-in", "Other"),
                "data", List.of(35, 25, 15, 15, 10)
        );
    }

    @Override
    public Map<String, Object> getRoomPerformance() {
        // Implement logic to get room performance
        return Map.of(
                "labels", List.of("Deluxe Suite", "Executive Room", "Luxury Suite", "Standard Room"),
                "occupancyData", List.of(85, 75, 65, 60),
                "revenueData", List.of(120000, 95000, 80000, 60000)
        );
    }

    // Helper methods
    private List<String> getMonthLabels(LocalDate start, LocalDate end) {
        // Generate month labels between start and end dates
        List<String> months = new ArrayList<>();
        LocalDate current = start;
        while (!current.isAfter(end)) {
            months.add(current.getMonth().toString().substring(0, 3));
            current = current.plusMonths(1);
        }
        return months;
    }

    private List<Long> getDataForMonths(Map<String, Long> data, LocalDate start, LocalDate end) {
        return getMonthLabels(start, end).stream()
                .map(month -> data.getOrDefault(month, 0L))
                .collect(Collectors.toList());
    }

}