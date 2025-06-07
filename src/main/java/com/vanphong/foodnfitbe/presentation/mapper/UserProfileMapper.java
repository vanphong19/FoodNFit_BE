package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.UserProfiles;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserProfileRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {
    public UserProfileResponse toResponse(UserProfiles userProfiles) {
        return UserProfileResponse.builder()
                .id(userProfiles.getId())
                .height(userProfiles.getHeight())
                .weight(userProfiles.getWeight())
                .tdee(userProfiles.getTdee())
                .mealGoal(userProfiles.getMealGoal())
                .exerciseGoal(userProfiles.getExerciseGoal())
                .createdAt(userProfiles.getCreatedAt())
                .build();
    }
}
