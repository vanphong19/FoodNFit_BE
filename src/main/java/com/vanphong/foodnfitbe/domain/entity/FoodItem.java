package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "food_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "calories")
    private Double calories;
    @Column(name = "protein")
    private Double protein;

    @Column(name = "carbs")
    private Double carbs;
    @Column(name = "fat")
    private Double fat;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "serving_size_en")
    private String servingSizeEn;

    @Column(name = "recipe_en")
    private String recipeEn;

    @Column(name = "name_vi")
    private String nameVi;
    @Column(name = "recipe_vi")
    private String recipeVi;
    @Column(name = "serving_size_vi")
    private String servingSizeVi;

    @Column(name = "ingredients_en")
    private String ingredientsEn;

    @Column(name = "food_type_id")
    private Integer foodTypeId;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "created_date")
    private LocalDate createdDate;
}
