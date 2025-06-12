package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHistoryResponse {
    private UUID id;
    private String email;
    private String fullname;
    private boolean gender;
    private LocalDate birthday;
    private String avatarUrl;
    private Boolean isActive;
    private LocalDateTime changedAt;
    private String changeType;
    private String changedBy;
}
