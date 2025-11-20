package com.fitfusion.controller;

import com.fitfusion.model.DietLog;
import com.fitfusion.model.User;
import com.fitfusion.model.UserProfile;
import com.fitfusion.model.WorkoutLog;
import com.fitfusion.service.DietService;
import com.fitfusion.service.ProfileService;
import com.fitfusion.service.UserService;
import com.fitfusion.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private DietService dietService;

    @GetMapping("/")
    public String landingPage(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            return "redirect:/dashboard";
        }
        return "index";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        UserProfile profile = profileService.getProfileByUserId(user.getId());

        if (profile == null) {
            return "redirect:/profile/setup";
        }

        // Daily Stats
        LocalDate today = LocalDate.now();
        List<WorkoutLog> workoutLogs = workoutService.getDailyWorkoutLogs(user.getId(), today);
        List<DietLog> dietLogs = dietService.getDailyDietLogs(user.getId(), today);

        // Calculate Nutrition Totals
        double totalCalories = dietLogs.stream().mapToDouble(l -> (l.getFoodItem().getCalories() * l.getQuantity()) / 100).sum();
        double totalProtein = dietLogs.stream().mapToDouble(l -> (l.getFoodItem().getProtein() * l.getQuantity()) / 100).sum();

        // Calculate Workout Stats
        int setsCompleted = workoutLogs.stream().mapToInt(WorkoutLog::getSetsCompleted).sum();
        int exercisesCount = (int) workoutLogs.stream().map(WorkoutLog::getExercise).distinct().count();

        model.addAttribute("user", user);
        model.addAttribute("profile", profile);
        model.addAttribute("calories", Math.round(totalCalories));
        model.addAttribute("protein", Math.round(totalProtein));
        model.addAttribute("setsCompleted", setsCompleted);
        model.addAttribute("exercisesCount", exercisesCount);
        
        // Check if API key is set
        model.addAttribute("hasApiKey", user.getGeminiApiKey() != null && !user.getGeminiApiKey().isEmpty());

        return "dashboard";
    }
}
