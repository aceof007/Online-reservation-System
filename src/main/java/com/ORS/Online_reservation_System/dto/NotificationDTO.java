package com.ORS.Online_reservation_System.dto;

import com.ORS.Online_reservation_System.model.NotificationStatus;
import com.ORS.Online_reservation_System.model.NotificationType;

import java.util.Date;

public class NotificationDTO {

    private Integer notificationId;
    private Integer userId;
    private NotificationType type;
    private String message;
    private Date date;
    private NotificationStatus status;
    private Boolean markAsRead;

    public NotificationDTO() {
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    public Boolean getMarkAsRead() {
        return markAsRead;
    }

    public void setMarkAsRead(Boolean markAsRead) {
        this.markAsRead = markAsRead;
    }
}
