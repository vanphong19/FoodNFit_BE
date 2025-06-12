package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface ExerciseJpaRepository extends JpaRepository<Exercise, Integer>, JpaSpecificationExecutor<Exercise> {
    List<Exercise> findByExerciseNameContainingIgnoreCase(String s);
    Long countByCreatedDateBetweenAndActiveTrue(LocalDate start, LocalDate end);
}