package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class MonthlyProfileResponse {
    private Float initialWeight;    // Cân nặng đầu tháng
    private Float currentWeight;    // Cân nặng cuối tháng (mới nhất trong tháng)
    private Float weightChange;     // Thay đổi cân nặng
    private Float currentBmi;       // Chỉ số BMI mới nhất trong tháng
    private String bmiStatus;       // Trạng thái BMI (vd: "Normal", "Overweight")
    private List<WeightHistoryData> weightHistory; // Lịch sử cân nặng trong tháng
}
