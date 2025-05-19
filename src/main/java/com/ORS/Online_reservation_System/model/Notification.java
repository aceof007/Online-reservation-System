package com.ORS.Online_reservation_System.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;
@Data
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notificationId;

    @Column(nullable = false)
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Column(nullable = false, length = 500)
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    private Boolean markAsRead;

    @Column(nullable = false)
    private Boolean resendNotification;

    public Boolean sendNotification() {
        // Logic to send notification (email, SMS, etc.)
        // Here we'll just simulate the sending process
        if (this.status != NotificationStatus.SENT) {
            this.status = NotificationStatus.SENT;
            this.date = new Date();
            return true;
        }
        return false;
    }

    public void markAsRead() {
        this.markAsRead = true;
    }

    public Boolean resendNotification() {
        if (this.status != NotificationStatus.PENDING) {
            this.resendNotification = true;
            this.status = NotificationStatus.PENDING;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(notificationId, that.notificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(notificationId);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", markAsRead=" + markAsRead +
                ", resendNotification=" + resendNotification +
                '}';
    }

}
