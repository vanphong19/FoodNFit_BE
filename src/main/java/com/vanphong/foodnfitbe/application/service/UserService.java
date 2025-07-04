package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserSearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserUpdateRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserDailyStatsDto;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(UUID id, UserUpdateRequest userRequest);
    UserResponse getById(UUID id);
    Page<UserResponse> getAllUsers(UserSearchCriteria criteria);
    UserResponse deleteUser(UUID id);
    UserResponse blockUser(UUID id);
    Long countUsersCreateAtLastMonth();
    Long countUsers();
    Long countUsersCreateAtThisMonth();
    UserDailyStatsDto getUserDailyStats();
}
