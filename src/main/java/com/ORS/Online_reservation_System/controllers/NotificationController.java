package com.ORS.Online_reservation_System.controllers;

import com.ORS.Online_reservation_System.dto.DateRangeDTO;
import com.ORS.Online_reservation_System.dto.NotificationDTO;
import com.ORS.Online_reservation_System.model.Notification;
import com.ORS.Online_reservation_System.model.NotificationStatus;
import com.ORS.Online_reservation_System.model.NotificationType;
import com.ORS.Online_reservation_System.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = convertToEntity(notificationDTO);
        Notification savedNotification = notificationService.saveNotification(notification);
        return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Integer id) {
        Optional<Notification> notification = notificationService.findById(id);
        return notification.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.findAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable Integer userId) {
        List<Notification> notifications = notificationService.findNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<Notification>> getUnreadNotificationsByUserId(@PathVariable Integer userId) {
        List<Notification> notifications = notificationService.findUnreadNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<Notification>> getNotificationsByUserIdAndStatus(
            @PathVariable Integer userId,
            @PathVariable NotificationStatus status) {
        List<Notification> notifications = notificationService.findNotificationsByUserIdAndStatus(userId, status);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/type/{type}")
    public ResponseEntity<List<Notification>> getNotificationsByUserIdAndType(
            @PathVariable Integer userId,
            @PathVariable NotificationType type) {
        List<Notification> notifications = notificationService.findNotificationsByUserIdAndType(userId, type);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping("/date-range")
    public ResponseEntity<List<Notification>> getNotificationsByDateRange(@RequestBody DateRangeDTO dateRangeDTO) {
        List<Notification> notifications = notificationService.findNotificationsByDateRange(
                dateRangeDTO.getStartDate(), dateRangeDTO.getEndDate());
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<Boolean> sendNotification(@PathVariable Integer id) {
        boolean sent = notificationService.sendNotification(id);
        return new ResponseEntity<>(sent, HttpStatus.OK);
    }

    @PostMapping("/{id}/resend")
    public ResponseEntity<Boolean> resendNotification(@PathVariable Integer id) {
        boolean resent = notificationService.resendNotification(id);
        return new ResponseEntity<>(resent, HttpStatus.OK);
    }

    @PostMapping("/{id}/mark-as-read")
    public ResponseEntity<Boolean> markNotificationAsRead(@PathVariable Integer id) {
        boolean marked = notificationService.markNotificationAsRead(id);
        return new ResponseEntity<>(marked, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Integer id) {
        boolean deleted = notificationService.deleteNotification(id);
        return deleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/process-unsent")
    public ResponseEntity<Void> processUnsentNotifications() {
        notificationService.processUnsentNotifications();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Notification convertToEntity(NotificationDTO notificationDTO) {
        Notification notification = new Notification();
        notification.setUserId(notificationDTO.getUserId());
        notification.setType(notificationDTO.getType());
        notification.setMessage(notificationDTO.getMessage());
        if (notificationDTO.getDate() != null) {
            notification.setDate(notificationDTO.getDate());
        }
        if (notificationDTO.getStatus() != null) {
            notification.setStatus(notificationDTO.getStatus());
        }
        if (notificationDTO.getMarkAsRead() != null) {
            notification.setMarkAsRead(notificationDTO.getMarkAsRead());
        }
        return notification;
    }
}
