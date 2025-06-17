package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

import java.time.LocalDate;
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
    Float bmi;
    String mealGoal;
    String exerciseGoal;
    LocalDateTime createdAt;
    LocalDate birthday;
    Boolean gender;
    String avtUrl;
    String fullname;
    String email;
}
