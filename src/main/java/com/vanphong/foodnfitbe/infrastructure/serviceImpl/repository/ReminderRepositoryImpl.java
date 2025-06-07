package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.domain.repository.ReminderRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.ReminderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class ReminderRepositoryImpl implements ReminderRepository {
    private final ReminderJpaRepository reminderJpaRepository;
    @Override
    public Reminders save(Reminders reminders) {
        return reminderJpaRepository.save(reminders);
    }

    @Override
    public Reminders findByScheduledTime(LocalDateTime scheduledTime) {
        return reminderJpaRepository.findByScheduledTime(scheduledTime);
    }
}
