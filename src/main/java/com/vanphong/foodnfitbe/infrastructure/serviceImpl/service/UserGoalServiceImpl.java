package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.UserGoalService;
import com.vanphong.foodnfitbe.domain.entity.UserGoal;
import com.vanphong.foodnfitbe.domain.entity.UserProfiles;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.UserGoalRepository;
import com.vanphong.foodnfitbe.domain.repository.UserProfileRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.UserGoalMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserGoalResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@Service
@Transactional
@RequiredArgsConstructor
public class UserGoalServiceImpl implements UserGoalService {
    private final UserGoalRepository userGoalRepository;
    private final CurrentUser currentUser;
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final UserGoalMapper userGoalMapper;

    private static final Map<String, double[]> MACRO_MAP = Map.of(
            "lose", new double[]{0.40, 0.30, 0.30},        // protein, fat, carbs %
            "maintain", new double[]{0.30, 0.30, 0.40},
            "gain", new double[]{0.25, 0.20, 0.55},
            "lean_gain", new double[]{0.35, 0.20, 0.45}
    );

    @Override
    public void createUserGoal(UUID userProfileId) {
        UUID userID = currentUser.getCurrentUserId();
        Users user = userRepository.findUser(userID).orElseThrow(() -> new RuntimeException("User not found"));
        Optional<UserProfiles> profiles = userProfileRepository.getLatestByUserID(userID);
        if(profiles.isEmpty()){
            throw new NotFoundException("user profile not found");
        }

        UserProfiles userProfiles = profiles.get();

        double tdee = userProfiles.getTdee();
        String mealGoal = userProfiles.getMealGoal().toLowerCase();

        if (!MACRO_MAP.containsKey(mealGoal)) {
            throw new IllegalArgumentException("Invalid meal goal: " + mealGoal);
        }

        double targetCalories = switch (mealGoal) {
            case "lose" -> tdee - 500;
            case "gain" -> tdee + 500;
            case "lean_gain" -> tdee + 300;
            default -> tdee;
        };

        double[] macro = MACRO_MAP.getOrDefault(mealGoal, MACRO_MAP.get("maintain"));
        double proteinPercent = macro[0];
        double fatPercent = macro[1];
        double carbPercent = macro[2];

        double targetProtein = targetCalories * proteinPercent / 4.0;
        double targetFat = targetCalories * fatPercent / 9.0;
        double targetCarb = targetCalories * carbPercent / 4.0;

        double caloriesBreakfast = targetCalories * 0.20;
        double caloriesLunch = targetCalories * 0.40;
        double caloriesDinner = targetCalories * 0.30;
        double caloriesSnack = targetCalories * 0.10;

        UserGoal userGoal = UserGoal.builder()
                .user(user)
                .targetCalories(targetCalories)
                .targetProtein(targetProtein)
                .targetFat(targetFat)
                .targetCarbs(targetCarb)
                .caloriesBreakfast(caloriesBreakfast)
                .caloriesLunch(caloriesLunch)
                .caloriesDinner(caloriesDinner)
                .caloriesSnack(caloriesSnack)
                .createdAt(LocalDate.now())
                .build();

        userGoalRepository.save(userGoal);
    }

    @Override
    public UserGoalResponse getUserGoalLatest() {
        Optional<UserGoal> userGoal = userGoalRepository.findLatest();
        if(userGoal.isEmpty()){
            throw new NotFoundException("user goal not found");
        }
        UserGoal goal = userGoal.get();
        return userGoalMapper.toResponse(goal);
    }
}
