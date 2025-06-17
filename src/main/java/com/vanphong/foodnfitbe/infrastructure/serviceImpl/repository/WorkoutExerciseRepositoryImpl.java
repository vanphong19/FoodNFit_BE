package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.WorkoutExercise;
import com.vanphong.foodnfitbe.domain.repository.WorkoutExerciseRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.WorkoutExerciseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WorkoutExerciseRepositoryImpl implements WorkoutExerciseRepository {
    private final WorkoutExerciseJpaRepository jpaRepository;

    @Override
    public WorkoutExercise save(WorkoutExercise workoutExercise) {
        return jpaRepository.save(workoutExercise);
    }

    @Override
    public void delete(Integer id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<WorkoutExercise> findById(Integer id) {
        return jpaRepository.findById(id);
    }

    @Override
    public void deleteByPlanId(Integer planId) {
        jpaRepository.deleteByPlanId(planId);
    }

    @Override
    public void saveAll(List<WorkoutExercise> workoutExercises) {
        jpaRepository.saveAll(workoutExercises);
    }
}
