package com.ORS.Online_reservation_System.services;

import java.util.Map;

public interface DashboardService {
    long getTotalProperties();
    long getTotalBookings();
    long getTotalGuests();
    double getOccupancyRate();

    Map<String, Object> getBookingAnalytics(String period);
    Map<String, Object> getRevenueByProperty(String period);
    Map<String, Object> getPerformanceMetrics();
    Map<String, Object> getBookingSources();
    Map<String, Object> getRoomPerformance();
}