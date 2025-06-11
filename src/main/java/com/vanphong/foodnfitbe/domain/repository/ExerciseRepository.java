package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.Exercise;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository {
    Exercise saveExercise(Exercise exercise);
    List<Exercise> findAllExercises();
    Optional<Exercise> findExerciseById(Integer id);
    void delete(Exercise exercise);
    List<Exercise> searchExercise(String keyword);
    Long count();
    Long countExercisesCreatedThisMonth(LocalDate from, LocalDate to);
}
