package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FoodTypeRequest {
    private String name;
    private String description;
}
