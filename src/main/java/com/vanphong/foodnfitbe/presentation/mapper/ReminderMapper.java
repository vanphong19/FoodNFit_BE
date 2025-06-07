package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.ReminderRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ReminderResponse;
import org.springframework.stereotype.Component;

@Component
public class ReminderMapper {
    public Reminders toEntity(ReminderRequest request){
        return Reminders.builder()
                .message(request.getMessage())
                .reminderType(request.getReminderType())
                .scheduledTime(request.getScheduledTime())
                .frequency(request.getFrequency())
                .isActive(false)
                .build();
    }
    public ReminderResponse toResponse(Reminders reminders){
        return ReminderResponse.builder()
                .id(reminders.getId())
                .message(reminders.getMessage())
                .reminderType(reminders.getReminderType())
                .scheduledTime(reminders.getScheduledTime())
                .frequency(reminders.getFrequency())
                .isActive(reminders.getIsActive())
                .build();
    }
}
