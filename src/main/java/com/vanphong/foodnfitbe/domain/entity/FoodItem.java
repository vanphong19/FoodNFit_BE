package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "name")
    private String name;
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

    @Column(name = "serving_size")
    private String servingSize;

    @Column(name = "recipe")
    private String recipe;

    @Column(name = "food_type_id")
    private Integer foodTypeId;

    @Column(name = "is_active")
    private Boolean isActive;

}
