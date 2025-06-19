package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "food_log")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "total_calories")
    private Double totalCalories;
    @Column(name = "total_protein")
    private Double totalProtein;
    @Column(name = "total_fat")
    private Double totalFat;
    @Column(name = "total_carbs")
    private Double totalCarbs;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "meal")
    private String meal;
    @OneToMany(mappedBy = "log", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodLogDetail> details = new ArrayList<>();
}