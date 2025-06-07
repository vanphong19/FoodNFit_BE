package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import com.vanphong.foodnfitbe.domain.entity.FoodLogDetail;
import com.vanphong.foodnfitbe.domain.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodLogResponse {
    private Integer id;
    private UUID userId;

    private Double totalCalories;
    private Double totalProtein;
    private Double totalFat;
    private Double totalCarbs;
    private LocalDate date;
    private String meal;
    private List<FoodLogDetailResponse> foodLogDetails;
}
