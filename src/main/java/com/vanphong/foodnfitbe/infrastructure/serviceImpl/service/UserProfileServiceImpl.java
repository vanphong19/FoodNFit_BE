package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.UserGoalService;
import com.vanphong.foodnfitbe.application.service.UserProfileService;
import com.vanphong.foodnfitbe.domain.entity.UserProfiles;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.UserProfileRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.UserJpaRepository;
import com.vanphong.foodnfitbe.presentation.mapper.UserProfileMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserProfileRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserProfileResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
@Service
@Transactional
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final CurrentUser currentUser;
    private final UserJpaRepository userJpaRepository;
    private final UserGoalService userGoalService;
    @Override
    public UserProfileResponse createUserProfile(UserProfileRequest userProfileRequest) {
        UUID userId = currentUser.getCurrentUserId();
        Users user = userJpaRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        boolean isGoalChange = false;
        Optional<UserProfiles> profiles = userProfileRepository.getLatestByUserID(userId);
        if (profiles.isEmpty()) {
            isGoalChange = true;
        }
        else {
            if(!profiles.get().getMealGoal().equals(userProfileRequest.getMealGoal())) {
                isGoalChange = true;
            }
        }

        UserProfiles userProfiles = UserProfiles.builder()
                .user(user)
                .height(userProfileRequest.getHeight())
                .weight(userProfileRequest.getWeight())
                .tdee(userProfileRequest.getTdee())
                .mealGoal(userProfileRequest.getMealGoal())
                .exerciseGoal(userProfileRequest.getExerciseGoal())
                .createdAt(LocalDateTime.now())
                .build();

        UserProfiles saved = userProfileRepository.save(userProfiles);

        UUID savedId = saved.getId();

        if(isGoalChange) {
            userGoalService.createUserGoal(savedId);
        }
        return userProfileMapper.toResponse(saved);
    }

    @Override
    public UserProfileResponse findByUserId(UUID userId) {
        Optional<UserProfiles> userProfiles = userProfileRepository.getLatestByUserID(userId);

        if(userProfiles.isEmpty()){
            throw new NotFoundException("User profile not found");
        }

        UserProfiles userProfile = userProfiles.get();
        return userProfileMapper.toResponse(userProfile);
    }
}
