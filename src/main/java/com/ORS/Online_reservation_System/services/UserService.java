package com.ORS.Online_reservation_System.services;

import com.ORS.Online_reservation_System.model.User;
import com.ORS.Online_reservation_System.model.UserStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<User> getAllUsers(int page, int size);
    Page<User> searchUsers(String query, int page, int size);
    void updateUserStatus(Long userId, UserStatus status);
    void deleteUsers(List<Long> userIds);
    
}