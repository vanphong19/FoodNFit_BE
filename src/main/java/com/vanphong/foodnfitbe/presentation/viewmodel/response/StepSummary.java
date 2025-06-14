package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import lombok.*;

import java.time.LocalDate;

public interface StepSummary {
    LocalDate getDate();
    Long getTotalSteps();
    Float getTotalDistance();
}
