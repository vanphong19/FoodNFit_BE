package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightHistoryData {
     LocalDate date;
     Float weight;
}
