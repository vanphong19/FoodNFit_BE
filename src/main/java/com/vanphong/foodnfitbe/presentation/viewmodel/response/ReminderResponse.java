package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReminderResponse {
    private Integer id;
    private String reminderType;
    private String message;
    private LocalDateTime scheduledTime;
    private Boolean isActive;
    private String frequency;
}
