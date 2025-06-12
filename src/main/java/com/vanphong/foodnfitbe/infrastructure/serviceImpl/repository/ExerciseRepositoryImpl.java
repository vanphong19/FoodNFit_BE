package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.repository.ExerciseRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.ExerciseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ExerciseRepositoryImpl implements ExerciseRepository {
    private final ExerciseJpaRepository exerciseJpaRepository;
    public ExerciseRepositoryImpl(ExerciseJpaRepository exerciseJpaRepository) {
        this.exerciseJpaRepository = exerciseJpaRepository;
    }
    @Override
    public Exercise saveExercise(Exercise exercise) {
        return exerciseJpaRepository.save(exercise);
    }

    @Override
    public Page<Exercise> findAllExercises(Specification<Exercise> specification, PageRequest pageRequest) {
        return exerciseJpaRepository.findAll(specification, pageRequest);
    }


    @Override
    public Optional<Exercise> findExerciseById(Integer id) {
        return exerciseJpaRepository.findById(id);
    }

    @Override
    public void delete(Exercise exercise) {
        exerciseJpaRepository.delete(exercise);
    }

    @Override
    public List<Exercise> searchExercise(String keyword) {
        return exerciseJpaRepository.findByExerciseNameContainingIgnoreCase(keyword);
    }

    @Override
    public Long count() {
        return exerciseJpaRepository.count();
    }

    @Override
    public Long countExercisesCreatedThisMonth(LocalDate from, LocalDate to) {
        return exerciseJpaRepository.countByCreatedDateBetweenAndActiveTrue(from, to);
    }

}
