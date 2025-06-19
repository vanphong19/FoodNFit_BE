package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.WorkoutPlanService;
import com.vanphong.foodnfitbe.domain.entity.StepsTracking;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import com.vanphong.foodnfitbe.domain.repository.StepsTrackingRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.domain.repository.WorkoutPlanRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.WorkoutPlanMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutPlanRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.*;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutPlanServiceImpl implements WorkoutPlanService {
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutPlanMapper workoutPlanMapper;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    private final StepsTrackingRepository stepsTrackingRepository;

    @Override
    public WorkoutPlanCreateResponse create(WorkoutPlanRequest request) {
        UUID userId = currentUser.getCurrentUserId();
        Users user = userRepository.findUser(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        WorkoutPlan plan = workoutPlanRepository.findByUserIdAndPlanDate(userId, today)
                .orElseGet(() -> WorkoutPlan.builder()
                        .user(user)
                        .planDate(today)
                        .exercises(new ArrayList<>())
                        .build()
                );

        plan.setExerciseCount(request.getExerciseCount());
        plan.setTotalCaloriesBurnt(request.getTotalCaloriesBurnt());

        WorkoutPlan saved = workoutPlanRepository.save(plan);
        return workoutPlanMapper.toCreateResponse(saved);
    }

    @Override
    public WorkoutPlanResponse update(Integer id, WorkoutPlanRequest request) {
        WorkoutPlan plan = workoutPlanRepository.findById(id).orElseThrow(() -> new NotFoundException("Workout not found"));

        plan.setTotalCaloriesBurnt(request.getTotalCaloriesBurnt());
        plan.setPlanDate(LocalDate.now());
        plan.setExerciseCount(request.getExerciseCount());
        return workoutPlanMapper.toResponse(workoutPlanRepository.save(plan));
    }

    @Override
    public void delete(Integer id) {
        WorkoutPlan plan = workoutPlanRepository.findById(id).orElseThrow(() -> new NotFoundException("Workout not found"));

        plan.getExercises().clear();
        workoutPlanRepository.delete(plan);
    }

    @Override
    public WorkoutPlanByDate getByDate(LocalDate date) {
        UUID id = currentUser.getCurrentUserId();
        WorkoutPlan plan = workoutPlanRepository.findByUserAndDate(id, date).orElseThrow(() -> new NotFoundException("Workout not found"));
        return workoutPlanMapper.toWorkoutPlanByDate(plan);
    }

    @Override
    public WeeklyExerciseSummaryResponse getWeeklyExerciseSummary(LocalDate date) {
        UUID userId = currentUser.getCurrentUserId(); // Get current user ID

        // Calculate week start and end dates
        LocalDate weekStart = date.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = date.with(DayOfWeek.SUNDAY);

        // Convert to LocalDateTime for steps tracking
        LocalDateTime weekStartTime = weekStart.atStartOfDay();
        LocalDateTime weekEndTime = weekEnd.atTime(23, 59, 59);

        // Get all workout plans for the week
        List<WorkoutPlan> weeklyPlans = workoutPlanRepository.findByUserIdAndDateRange(userId, weekStart, weekEnd);

        // Calculate total calories burnt
        Double totalCaloriesBurnt = weeklyPlans.stream()
                .mapToDouble(plan -> plan.getTotalCaloriesBurnt() != null ? plan.getTotalCaloriesBurnt() : 0.0)
                .sum();

        // Calculate total training sessions
        Integer totalTrainingSessions = weeklyPlans.size();

        // Calculate average calories per session
        Double averageCaloriesPerSession = totalTrainingSessions > 0 ?
                totalCaloriesBurnt / totalTrainingSessions : 0.0;

        // Find best day (day with highest calories burnt)
        String bestDay = findBestDay(weeklyPlans);

        // Create daily calories chart data
        List<DailyCaloriesExerciseDto> dailyCalorieChartData = createDailyCaloriesChart(weeklyPlans, weekStart, weekEnd);

        // Get favorite exercises
        List<FavoriteExerciseDto> favoriteExercises = getFavoriteExercises(userId, weekStart, weekEnd);

        // Calculate total steps for the week
        Integer totalSteps = calculateTotalSteps(userId, weekStartTime, weekEndTime);

        return WeeklyExerciseSummaryResponse.builder()
                .totalCaloriesBurnt(totalCaloriesBurnt)
                .totalTrainingSessions(totalTrainingSessions)
                .totalSteps(totalSteps)
                .averageCaloriesPerSession(averageCaloriesPerSession)
                .bestDay(bestDay)
                .dailyCalorieChartData(dailyCalorieChartData)
                .favoriteExercises(favoriteExercises)
                .build();
    }

    private String findBestDay(List<WorkoutPlan> weeklyPlans) {
        return weeklyPlans.stream()
                .max(Comparator.comparing(plan -> plan.getTotalCaloriesBurnt() != null ? plan.getTotalCaloriesBurnt() : 0.0))
                .map(plan -> plan.getPlanDate().getDayOfWeek().toString())
                .orElse("MONDAY");
    }

    private List<DailyCaloriesExerciseDto> createDailyCaloriesChart(List<WorkoutPlan> weeklyPlans, LocalDate weekStart, LocalDate weekEnd) {
        Map<LocalDate, DailyCaloriesExerciseDto> dailyDataMap = new HashMap<>();

        // Initialize all days of the week with zero values
        for (LocalDate date = weekStart; !date.isAfter(weekEnd); date = date.plusDays(1)) {
            dailyDataMap.put(date, new DailyCaloriesExerciseDto(date, 0.0, 0));
        }

        // Fill in actual data
        for (WorkoutPlan plan : weeklyPlans) {
            LocalDate planDate = plan.getPlanDate();
            Double calories = plan.getTotalCaloriesBurnt() != null ? plan.getTotalCaloriesBurnt() : 0.0;
            Integer exerciseCount = plan.getExerciseCount() != null ? plan.getExerciseCount() : 0;

            dailyDataMap.put(planDate, new DailyCaloriesExerciseDto(planDate, calories, exerciseCount));
        }

        // Return sorted by date
        return dailyDataMap.values().stream()
                .sorted(Comparator.comparing(DailyCaloriesExerciseDto::getDate))
                .collect(Collectors.toList());
    }

    private List<FavoriteExerciseDto> getFavoriteExercises(UUID userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = workoutPlanRepository.findFavoriteExercisesByUserAndDateRange(userId, startDate, endDate);

        return results.stream()
                .limit(5) // Get top 5 favorite exercises
                .map(result -> new FavoriteExerciseDto(
                        (String) result[0], // exerciseName
                        ((Long) result[1]).intValue(), // totalSessions
                        (Double) result[2], // totalCalories
                        (String) result[3] // muscleGroup
                ))
                .collect(Collectors.toList());
    }

    private Integer calculateTotalSteps(UUID userId, LocalDateTime startTime, LocalDateTime endTime) {
        List<StepsTracking> stepsTrackingList = stepsTrackingRepository.findByUserIdAndTimeRange(userId, startTime, endTime);

        return stepsTrackingList.stream()
                .mapToInt(steps -> steps.getStepsCount() != null ? steps.getStepsCount() : 0)
                .sum();
    }
}
