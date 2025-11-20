package com.fitfusion.controller;

import com.fitfusion.model.*;
import com.fitfusion.service.ExerciseService;
import com.fitfusion.service.UserService;
import com.fitfusion.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/workouts")
    public String viewWorkouts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<WorkoutPlan> plans = workoutService.getUserWorkoutPlans(user.getId());
        model.addAttribute("plans", plans);
        return "workouts"; // List of plans
    }

    @GetMapping("/workouts/builder")
    public String viewWorkoutBuilder(Model model) {
        model.addAttribute("exercises", exerciseService.getAllExercises());
        return "workout-builder";
    }

    // Simplified creation for now - in a real app this might be a complex JSON post
    @PostMapping("/workouts/create")
    public String createWorkoutPlan(@AuthenticationPrincipal UserDetails userDetails, 
                                    @RequestParam String name, 
                                    @RequestParam String description,
                                    @RequestParam(required = false) List<Long> exerciseIds,
                                    @RequestParam(required = false) List<Integer> sets,
                                    @RequestParam(required = false) List<Integer> reps) {
        
        User user = userService.findByUsername(userDetails.getUsername());
        WorkoutPlan plan = new WorkoutPlan();
        plan.setUser(user);
        plan.setName(name);
        plan.setDescription(description);

        List<WorkoutPlanItem> items = new ArrayList<>();
        if (exerciseIds != null) {
            for (int i = 0; i < exerciseIds.size(); i++) {
                WorkoutPlanItem item = new WorkoutPlanItem();
                item.setExercise(exerciseService.getExerciseById(exerciseIds.get(i)).orElseThrow());
                item.setSets(sets.get(i));
                item.setReps(reps.get(i));
                items.add(item);
            }
        }

        workoutService.createWorkoutPlan(plan, items);
        return "redirect:/workouts";
    }

    @GetMapping("/workouts/log")
    public String viewWorkoutLog(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("plans", workoutService.getUserWorkoutPlans(user.getId()));
        model.addAttribute("exercises", exerciseService.getAllExercises());
        model.addAttribute("todayLogs", workoutService.getDailyWorkoutLogs(user.getId(), LocalDate.now()));
        return "workout-log";
    }

    @PostMapping("/workouts/log")
    public String logWorkout(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam Long exerciseId,
                             @RequestParam Integer sets,
                             @RequestParam Integer reps,
                             @RequestParam Double weight) {
        User user = userService.findByUsername(userDetails.getUsername());
        WorkoutLog log = new WorkoutLog();
        log.setUser(user);
        log.setDate(LocalDate.now());
        log.setExercise(exerciseService.getExerciseById(exerciseId).orElseThrow());
        log.setSetsCompleted(sets);
        log.setRepsCompleted(reps);
        log.setWeight(weight);
        
        workoutService.logWorkout(log);
        return "redirect:/workouts/log";
    }
}
