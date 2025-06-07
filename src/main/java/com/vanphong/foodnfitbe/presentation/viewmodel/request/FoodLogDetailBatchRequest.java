package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Data
@Builder
@Setter
@Getter
public class FoodLogDetailBatchRequest {
    private Integer logId;
    private List<FoodLogDetailRequest> details;
}
