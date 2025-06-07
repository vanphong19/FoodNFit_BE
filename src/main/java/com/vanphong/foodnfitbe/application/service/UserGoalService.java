package com.vanphong.foodnfitbe.application.service;

import com.vanphong.foodnfitbe.domain.entity.UserGoal;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserGoalResponse;

import java.util.UUID;

public interface UserGoalService {
    void createUserGoal(UUID userProfileId);
    UserGoalResponse getUserGoalLatest();
}
