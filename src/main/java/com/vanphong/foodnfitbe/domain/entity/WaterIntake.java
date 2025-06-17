package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "water_intake")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaterIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // dùng serial
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "cups")
    private Integer cups;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public void addCups(int additionalCups) {
        this.cups += additionalCups;
        this.updatedAt = LocalDateTime.now();
    }
}
