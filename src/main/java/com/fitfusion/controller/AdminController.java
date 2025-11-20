package com.fitfusion.controller;

import com.fitfusion.model.Exercise;
import com.fitfusion.model.MuscleGroup;
import com.fitfusion.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping
    public String adminDashboard() {
        return "admin/dashboard";
    }

    // --- Muscle Groups ---

    @GetMapping("/muscle-groups/create")
    public String createMuscleGroupForm(Model model) {
        model.addAttribute("muscleGroup", new MuscleGroup());
        return "admin/muscle-group-form";
    }

    @PostMapping("/muscle-groups/create")
    public String saveMuscleGroup(@ModelAttribute MuscleGroup muscleGroup) {
        exerciseService.saveMuscleGroup(muscleGroup);
        return "redirect:/admin";
    }

    // --- Exercises ---

    @GetMapping("/exercises")
    public String listExercises(Model model) {
        model.addAttribute("exercises", exerciseService.getAllExercises());
        return "admin/manage-exercises";
    }

    @GetMapping("/exercises/create")
    public String createExerciseForm(Model model) {
        model.addAttribute("exercise", new Exercise());
        model.addAttribute("muscleGroups", exerciseService.getAllMuscleGroups());
        return "admin/exercise-form";
    }

    @PostMapping("/exercises/create")
    public String saveExercise(@ModelAttribute Exercise exercise) {
        exerciseService.saveExercise(exercise);
        return "redirect:/admin/exercises";
    }
    
    @GetMapping("/exercises/edit/{id}")
    public String editExerciseForm(@PathVariable Long id, Model model) {
        Exercise exercise = exerciseService.getExerciseById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid exercise Id:" + id));
        model.addAttribute("exercise", exercise);
        model.addAttribute("muscleGroups", exerciseService.getAllMuscleGroups());
        return "admin/exercise-form";
    }

    // --- Food Items ---

    @Autowired
    private com.fitfusion.service.DietService dietService;

    @GetMapping("/foods")
    public String listFoods(Model model) {
        model.addAttribute("foods", dietService.getAllFoodItems());
        return "admin/manage-foods";
    }

    @GetMapping("/foods/create")
    public String createFoodForm(Model model) {
        model.addAttribute("foodItem", new com.fitfusion.model.FoodItem());
        return "admin/food-form";
    }

    @PostMapping("/foods/create")
    public String saveFood(@ModelAttribute com.fitfusion.model.FoodItem foodItem) {
        dietService.saveFoodItem(foodItem);
        return "redirect:/admin/foods";
    }
}
