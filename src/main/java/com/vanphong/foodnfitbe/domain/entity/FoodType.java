package com.vanphong.foodnfitbe.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
}
