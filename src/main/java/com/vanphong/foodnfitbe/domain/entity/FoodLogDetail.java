package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_log_detail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FoodLogDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "log_id", nullable = false)
    private FoodLog log;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private FoodItem foodItem;

    @Column(name = "serving_size")
    private String servingSize;

    @Column(name = "calories")
    private Double calories;

    @Column(name = "carbs")
    private Double carbs;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "fat")
    private Double fat;
}

