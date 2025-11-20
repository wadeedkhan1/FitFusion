package com.fitfusion.service;

import com.fitfusion.model.Exercise;
import com.fitfusion.model.MuscleGroup;
import com.fitfusion.repository.ExerciseRepository;
import com.fitfusion.repository.MuscleGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private MuscleGroupRepository muscleGroupRepository;

    public List<MuscleGroup> getAllMuscleGroups() {
        return muscleGroupRepository.findAll();
    }

    public Optional<MuscleGroup> getMuscleGroupById(Long id) {
        return muscleGroupRepository.findById(id);
    }

    public List<Exercise> getExercisesByMuscleGroup(Long muscleGroupId) {
        return exerciseRepository.findByMuscleGroupId(muscleGroupId);
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public Optional<Exercise> getExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
    
    public MuscleGroup saveMuscleGroup(MuscleGroup muscleGroup) {
        return muscleGroupRepository.save(muscleGroup);
    }
}
