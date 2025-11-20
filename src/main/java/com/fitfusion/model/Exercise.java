package com.fitfusion.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "exercises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "muscle_group_id")
    private MuscleGroup muscleGroup;

    private String exerciseType; // Strength, Cardio, etc.
    private String equipmentRequired;
    private String mechanics; // Isolation, Compound
    private String forceType; // Push, Pull
    private String experienceLevel; // Beginner, Intermediate, Expert
    private String secondaryMuscles;

    @Column(columnDefinition = "TEXT")
    private String overview;

    @Column(columnDefinition = "TEXT")
    private String instructions; // Stored as JSON or simple text with newlines

    @Column(columnDefinition = "TEXT")
    private String tips; // Stored as JSON or simple text with newlines

    private String videoUrl;
    private String imageUrl;
}
