package com.ORS.Online_reservation_System.repositories;

import com.ORS.Online_reservation_System.model.Notification;
import com.ORS.Online_reservation_System.model.NotificationStatus;
import com.ORS.Online_reservation_System.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findByUserId(Integer userId);

    List<Notification> findByUserIdAndMarkAsReadFalse(Integer userId);

    List<Notification> findByUserIdAndStatus(Integer userId, NotificationStatus status);

    List<Notification> findByUserIdAndType(Integer userId, NotificationType type);

    List<Notification> findByDateBetween(Date startDate, Date endDate);

    List<Notification> findByStatusAndResendNotificationTrue(NotificationStatus status);
}
