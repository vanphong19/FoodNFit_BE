package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import com.vanphong.foodnfitbe.domain.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
public class WaterIntakeResponse {
    Integer id;

     UUID userId;

     Integer cups;

     LocalDate date;

     LocalDateTime updatedAt = LocalDateTime.now();
}
