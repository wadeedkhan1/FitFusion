package com.fitfusion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "food_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer calories; // per 100g or per unit
    private Double protein; // in grams
    private Double carbs; // in grams
    private Double fats; // in grams

    private Boolean isLocal; // True for Pakistani foods
}
