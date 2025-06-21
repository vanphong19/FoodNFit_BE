package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.Reminders;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReminderRepository {
    Reminders save(Reminders reminders);
    Reminders findByScheduledTime(LocalDateTime scheduledTime);
    List<Reminders> findAllByUserId(UUID userId);
    Optional<Reminders> findById(Integer id);
}
