package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.WorkoutExerciseService;
import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.entity.WorkoutExercise;
import com.vanphong.foodnfitbe.domain.entity.WorkoutPlan;
import com.vanphong.foodnfitbe.domain.repository.ExerciseRepository;
import com.vanphong.foodnfitbe.domain.repository.WorkoutExerciseRepository;
import com.vanphong.foodnfitbe.domain.repository.WorkoutPlanRepository;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.WorkoutExerciseBatchRequest;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.conscrypt.OAEPParameters;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkoutExerciseServiceImpl implements WorkoutExerciseService {
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final ExerciseRepository exerciseRepository;
    @Override
    public void saveAll(WorkoutExerciseBatchRequest request) {
        WorkoutPlan plan = workoutPlanRepository.findById(request.getWorkoutId()).orElseThrow(() -> new NotFoundException("Plan not found"));

        workoutExerciseRepository.deleteByPlanId(plan.getId());

        List<WorkoutExercise> details = request.getWorkoutExerciseRequests().stream().map(dto ->{
            Exercise exercise = exerciseRepository.findExerciseById(dto.getExerciseId()).orElseThrow(() -> new NotFoundException("Exercise not found"));
            return WorkoutExercise.builder()
                    .exercise(exercise)
                    .plan(plan)
                    .sets(dto.getSets())
                    .reps(dto.getReps())
                    .restTimeSecond(dto.getRestTimeSecond())
                    .minutes(dto.getMinutes())
                    .isCompleted(false)
                    .build();
        }).toList();

        workoutExerciseRepository.saveAll(details);
    }

    @Override
    public void delete(Integer id) {
        Optional<WorkoutExercise> workoutExercise = workoutExerciseRepository.findById(id);
        if (workoutExercise.isEmpty()) {
            throw new NotFoundException("Workout exercise not found");
        }
        workoutExerciseRepository.delete(id);
    }

    @Override
    public void     changeStatus(Integer id) {
        Optional<WorkoutExercise> workoutExercise = workoutExerciseRepository.findById(id);
        if (workoutExercise.isEmpty()) {
            throw new NotFoundException("Workout exercise not found");
        }

        WorkoutExercise exercise = workoutExercise.get();
        exercise.setIsCompleted(true);
    }
}
