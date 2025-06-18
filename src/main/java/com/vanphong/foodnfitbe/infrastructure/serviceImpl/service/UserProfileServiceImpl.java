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
import com.vanphong.foodnfitbe.presentation.viewmodel.response.MonthlyProfileResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserProfileResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WeightHistoryData;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

        Float height = userProfileRequest.getHeight();
        Float weight = userProfileRequest.getWeight();

        Float heightInMeters = height / 100f;
        Float bmi = weight / (heightInMeters * heightInMeters);

        UserProfiles userProfiles = UserProfiles.builder()
                .user(user)
                .height(userProfileRequest.getHeight())
                .weight(userProfileRequest.getWeight())
                .mealGoal(userProfileRequest.getMealGoal())
                .tdee(userProfileRequest.getTdee())
                .bmi(bmi)
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
    public UserProfileResponse findByUserId() {
        UUID userId = currentUser.getCurrentUserId();
        Optional<UserProfiles> userProfiles = userProfileRepository.getLatestByUserID(userId);

        if(userProfiles.isEmpty()){
            throw new NotFoundException("User profile not found");
        }

        UserProfiles userProfile = userProfiles.get();
        return userProfileMapper.toResponse(userProfile);
    }

    @Override
    public MonthlyProfileResponse getMonthlyProfile(int year, int month) {
        UUID userId = currentUser.getCurrentUserId();
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);

        List<UserProfiles> profilesInMonth = userProfileRepository.findByUserIdAndDateRange(userId, startOfMonth, endOfMonth);

        if(profilesInMonth.isEmpty()){
            return MonthlyProfileResponse.builder()
                    .weightHistory(Collections.emptyList())
                    .build();
        }
        UserProfiles initialProfile = profilesInMonth.getFirst();
        UserProfiles currentProfile = profilesInMonth.getLast();

        Float initialWeight = initialProfile.getWeight();
        Float currentWeight = currentProfile.getWeight();
        Float weightChange = currentWeight - initialWeight;
        Float currentBmi = currentProfile.getBmi();

        List<WeightHistoryData> weightHistory = profilesInMonth.stream()
                .map(profile -> new WeightHistoryData(
                        profile.getCreatedAt().toLocalDate(),
                        profile.getWeight()))
                .collect(Collectors.toList());

        // 5. Build và trả về response
        return MonthlyProfileResponse.builder()
                .initialWeight(initialWeight)
                .currentWeight(currentWeight)
                .weightChange(weightChange)
                .currentBmi(currentBmi)
                .bmiStatus(determineBmiStatus(currentBmi)) // hàm helper để xác định trạng thái BMI
                .weightHistory(weightHistory)
                .build();
    }

    private String determineBmiStatus(Float bmi) {
        if (bmi == null) return "";
        if (bmi < 18.5f) return "Underweight";
        if (bmi < 25f) return "Normal";
        if (bmi < 30f) return "Overweight";
        if (bmi < 35f) return "Severe Obesity";
        if (bmi < 40f) return "Morbid Obesity";
        return "Super Obesity";
    }
}
