package com.ORS.Online_reservation_System.serviceimplementation;

import com.ORS.Online_reservation_System.model.User;
import com.ORS.Online_reservation_System.model.UserStatus;
import com.ORS.Online_reservation_System.repositories.UserRepository;
import com.ORS.Online_reservation_System.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    public Page<User> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<User> searchUsers(String query, int page, int size) {
        return userRepository.findByUsernameContainingOrEmailContaining(
                query, PageRequest.of(page, size));
    }


//    @Override
//    public Page<User> searchUsers(String query, int page, int size) {
//        return userRepository.findByUsernameContainingOrEmailContaining(
//                query, query, PageRequest.of(page, size));
//    }

    @Override
    public void updateUserStatus(Long userId, UserStatus status) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status.toString());
        userRepository.save(user);
    }

    @Override
    public void deleteUsers(List<Long> userIds) {
        userRepository.deleteAllById(userIds);
    }


}
