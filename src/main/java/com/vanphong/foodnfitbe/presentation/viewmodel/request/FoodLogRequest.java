package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FoodLogRequest {
    @NotNull(message = "Calories must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Calories must be non-negative")
    private Double totalCalories;

    @NotNull(message = "Protein must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Protein must be non-negative")
    private Double totalProtein;

    @NotNull(message = "Fat must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Fat must be non-negative")
    private Double totalFat;

    @NotNull(message = "Carbs must not be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Carbs must be non-negative")
    private Double totalCarbs;

    @NotBlank(message = "Meal must not be blank")
    private String meal;
}
