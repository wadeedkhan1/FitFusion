package com.fitfusion.controller;

import com.fitfusion.model.Exercise;
import com.fitfusion.model.MuscleGroup;
import com.fitfusion.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/exercises")
    public String viewExerciseLibrary(Model model) {
        List<MuscleGroup> muscleGroups = exerciseService.getAllMuscleGroups();
        model.addAttribute("muscleGroups", muscleGroups);
        return "exercise-library";
    }

    @GetMapping("/exercises/muscle/{muscleGroupId}")
    public String viewExercisesByMuscle(@PathVariable Long muscleGroupId, Model model) {
        MuscleGroup muscleGroup = exerciseService.getMuscleGroupById(muscleGroupId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid muscle group Id:" + muscleGroupId));
        
        List<Exercise> exercises = exerciseService.getExercisesByMuscleGroup(muscleGroupId);
        
        model.addAttribute("muscleGroup", muscleGroup);
        model.addAttribute("exercises", exercises);
        return "exercises-by-muscle";
    }

    @GetMapping("/exercises/{id}")
    public String viewExerciseDetail(@PathVariable Long id, Model model) {
        Exercise exercise = exerciseService.getExerciseById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid exercise Id:" + id));
        
        model.addAttribute("exercise", exercise);
        return "exercise-detail";
    }
}
