package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.Reminders;

import java.time.LocalDateTime;

public interface ReminderRepository {
    Reminders save(Reminders reminders);
    Reminders findByScheduledTime(LocalDateTime scheduledTime);
}
