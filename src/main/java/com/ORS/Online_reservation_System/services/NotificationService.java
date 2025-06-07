package com.ORS.Online_reservation_System.services;

import com.ORS.Online_reservation_System.model.Notification;
import com.ORS.Online_reservation_System.model.NotificationStatus;
import com.ORS.Online_reservation_System.model.NotificationType;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NotificationService {

    Notification saveNotification(Notification notification);

    Optional<Notification> findById(Integer id);

    List<Notification> findAllNotifications();

    List<Notification> findNotificationsByUserId(Integer userId);

    List<Notification> findUnreadNotificationsByUserId(Integer userId);

    List<Notification> findNotificationsByUserIdAndStatus(Integer userId, NotificationStatus status);

    List<Notification> findNotificationsByUserIdAndType(Integer userId, NotificationType type);

    List<Notification> findNotificationsByDateRange(Date startDate, Date endDate);

    boolean sendNotification(Integer id);

    boolean resendNotification(Integer id);

    boolean markNotificationAsRead(Integer id);

    boolean deleteNotification(Integer id);

    void processUnsentNotifications();

}
