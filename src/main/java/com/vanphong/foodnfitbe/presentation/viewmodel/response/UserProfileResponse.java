package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserProfileResponse {
    UUID id;
    Float height;
    Float weight;
    Float tdee;
    String mealGoal;
    String exerciseGoal;
    LocalDateTime createdAt;
}
