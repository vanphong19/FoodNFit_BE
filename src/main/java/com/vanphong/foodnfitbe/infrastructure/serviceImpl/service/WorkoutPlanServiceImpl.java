package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.WorkoutPlanService;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.domain.repository.WorkoutPlanRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.WorkoutPlanMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutPlanRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.WorkoutPlanResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutPlanServiceImpl implements WorkoutPlanService {
    private final WorkoutPlanRepository workoutPlanRepository;
    private final WorkoutPlanMapper workoutPlanMapper;
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    @Override
    public WorkoutPlanResponse create(WorkoutPlanRequest request) {
        UUID userId = currentUser.getCurrentUserId();
        Users user = userRepository.findUser(userId).orElseThrow(() -> new NotFoundException("User not found"));
        WorkoutPlan plan = WorkoutPlan.builder()
                .user(user)
                .totalCaloriesBurnt(request.getTotalCaloriesBurnt())
                .planDate(LocalDate.now())
                .exerciseCount(request.getExerciseCount())
                .build();
        WorkoutPlan savedPlan = workoutPlanRepository.save(plan);
        return workoutPlanMapper.toResponse(savedPlan);
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
    public WorkoutPlanResponse getByDate(LocalDate date) {
        UUID id = currentUser.getCurrentUserId();
        WorkoutPlan plan = workoutPlanRepository.findByUserAndDate(id, date).orElseThrow(() -> new NotFoundException("Workout not found"));
        return workoutPlanMapper.toResponse(plan);
    }
}
