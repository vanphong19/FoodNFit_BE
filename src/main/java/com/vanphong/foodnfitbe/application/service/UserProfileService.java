package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserProfileRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.MonthlyProfileResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserProfileResponse;

import java.util.UUID;

public interface UserProfileService {
    UserProfileResponse createUserProfile(UserProfileRequest userProfileRequest);
    UserProfileResponse findByUserId();
    MonthlyProfileResponse getMonthlyProfile(int year, int month);
}
