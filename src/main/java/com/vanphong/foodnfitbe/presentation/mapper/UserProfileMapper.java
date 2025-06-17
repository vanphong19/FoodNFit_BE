package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.UserProfiles;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserProfileRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {
    public UserProfileResponse toResponse(UserProfiles profile) {
        Users user = profile.getUser();

        return UserProfileResponse.builder()
                .id(profile.getId())
                .height(profile.getHeight())
                .weight(profile.getWeight())
                .tdee(profile.getTdee())
                .bmi(profile.getBmi())
                .mealGoal(profile.getMealGoal())
                .exerciseGoal(profile.getExerciseGoal())
                .createdAt(profile.getCreatedAt())
                // lấy từ user
                .birthday(user.getBirthday())
                .gender(user.isGender())
                .avtUrl(user.getAvatarUrl())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .build();
    }

}
