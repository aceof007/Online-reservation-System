package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.Notification;
import com.ORS.Online_reservation_System.model.NotificationStatus;
import com.ORS.Online_reservation_System.model.NotificationType;
import com.ORS.Online_reservation_System.repositories.NotificationRepository;
import com.ORS.Online_reservation_System.services.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public Notification saveNotification(Notification notification) {
        // Set default values if not provided
        if (notification.getDate() == null) {
            notification.setDate(new Date());
        }
        if (notification.getStatus() == null) {
            notification.setStatus(NotificationStatus.PENDING);
        }
        if (notification.getMarkAsRead() == null) {
            notification.setMarkAsRead(false);
        }
        if (notification.getResendNotification() == null) {
            notification.setResendNotification(false);
        }

        return notificationRepository.save(notification);
    }

    @Override
    public Optional<Notification> findById(Integer id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public List<Notification> findNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserId(userId);
    }

    @Override
    public List<Notification> findUnreadNotificationsByUserId(Integer userId) {
        return notificationRepository.findByUserIdAndMarkAsReadFalse(userId);
    }

    @Override
    public List<Notification> findNotificationsByUserIdAndStatus(Integer userId, NotificationStatus status) {
        return notificationRepository.findByUserIdAndStatus(userId, status);
    }

    @Override
    public List<Notification> findNotificationsByUserIdAndType(Integer userId, NotificationType type) {
        return notificationRepository.findByUserIdAndType(userId, type);
    }

    @Override
    public List<Notification> findNotificationsByDateRange(Date startDate, Date endDate) {
        return notificationRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    @Transactional
    public boolean sendNotification(Integer id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            boolean sent = notification.sendNotification();
            if (sent) {
                notificationRepository.save(notification);
            }
            return sent;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean resendNotification(Integer id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            boolean resent = notification.resendNotification();
            if (resent) {
                notificationRepository.save(notification);
            }
            return resent;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean markNotificationAsRead(Integer id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.markAsRead();
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteNotification(Integer id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void processUnsentNotifications() {
        List<Notification> unsent = notificationRepository.findByStatusAndResendNotificationTrue(NotificationStatus.PENDING);
        for (Notification notification : unsent) {
            notification.sendNotification();
            notification.setResendNotification(false);
            notificationRepository.save(notification);
        }
    }

}
