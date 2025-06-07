package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import com.vanphong.foodnfitbe.domain.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserProfileRequest {
    Float height;
    Float weight;
    Float tdee;
    String mealGoal;
    String exerciseGoal;
}
