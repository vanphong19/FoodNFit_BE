package com.vanphong.foodnfitbe.infrastructure.jpaRepository;

import com.vanphong.foodnfitbe.domain.entity.Reminders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderJpaRepository extends JpaRepository<Reminders, Integer> {
    @Query("SELECT r FROM Reminders r WHERE r.isActive = false AND r.scheduledTime <= :now AND r.frequency = :frequency")
    List<Reminders> findDueReminders(@Param("now") LocalDateTime now, @Param("frequency") String frequency);
    Reminders findByScheduledTime(LocalDateTime scheduledTime);
}
