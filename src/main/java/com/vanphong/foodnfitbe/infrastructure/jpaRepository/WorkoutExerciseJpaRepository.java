package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.WorkoutExercise;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WorkoutExerciseJpaRepository extends JpaRepository<WorkoutExercise, Integer> {
    @Modifying
    @Transactional
    @Query("delete from WorkoutExercise d where d.plan.id = :planId")
    void deleteByPlanId(int planId);
}
