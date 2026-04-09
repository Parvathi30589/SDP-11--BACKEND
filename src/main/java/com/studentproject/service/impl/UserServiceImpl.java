package com.studentproject.service.impl;

import com.studentproject.dto.user.UpdateUserRequest;
import com.studentproject.dto.user.UserResponse;
import com.studentproject.entity.User;
import com.studentproject.exception.UnauthorizedException;
import com.studentproject.repository.UserRepository;
import com.studentproject.service.UserService;
import com.studentproject.util.AuthContextUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthContextUtil authContextUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getCurrentUser() {
        return map(authContextUtil.getCurrentUser());
    }

    @Override
    public UserResponse updateCurrentUser(UpdateUserRequest request) {
        User current = authContextUtil.getCurrentUser();
        current.setName(request.getName());
        current.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            current.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return map(userRepository.save(current));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        User current = authContextUtil.getCurrentUser();
        if (!"TEACHER".equals(current.getRole().name())) {
            throw new UnauthorizedException("Only teachers can view all users");
        }
        return userRepository.findAll().stream().map(this::map).toList();
    }

    private UserResponse map(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
