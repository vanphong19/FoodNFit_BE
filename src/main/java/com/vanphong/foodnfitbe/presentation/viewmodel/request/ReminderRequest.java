package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReminderRequest {
    private String reminderType;
    private String message;
    private LocalDateTime scheduledTime;
    private String frequency;
}
