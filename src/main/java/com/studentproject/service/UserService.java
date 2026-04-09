package com.studentproject.service;

import com.studentproject.dto.user.UpdateUserRequest;
import com.studentproject.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getCurrentUser();
    UserResponse updateCurrentUser(UpdateUserRequest request);
    List<UserResponse> getAllUsers();
}
