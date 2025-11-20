package com.fitfusion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "user_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Integer age;
    private Double height; // in cm
    private Double weight; // in kg
    private String gender;
    private String goal; // e.g., "LOSE_WEIGHT", "GAIN_MUSCLE", "MAINTAIN"
    private String activityLevel; // e.g., "SEDENTARY", "ACTIVE"
}
