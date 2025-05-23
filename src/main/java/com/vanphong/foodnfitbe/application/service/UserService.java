package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(UUID id, UserRequest userRequest);
    UserResponse getById(UUID id);
    List<UserResponse> getAllUsers();
    UserResponse deleteUser(UUID id);
    UserResponse blockUser(UUID id);
}
