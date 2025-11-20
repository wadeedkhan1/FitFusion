package com.fitfusion.controller;

import com.fitfusion.model.User;
import com.fitfusion.model.UserProfile;
import com.fitfusion.service.GeminiService;
import com.fitfusion.service.ProfileService;
import com.fitfusion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @PostMapping("/workout-recommendation")
    public Map<String, String> getWorkoutRecommendation(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        UserProfile profile = profileService.getProfileByUserId(user.getId());

        String prompt = String.format(
            "I am a %d year old %s, %d cm tall, weighing %.1f kg. My goal is to %s and my activity level is %s. " +
            "Please suggest a workout routine for me. Keep it concise and formatted as a list.",
            profile.getAge(), profile.getGender(), profile.getHeight().intValue(), profile.getWeight(), 
            profile.getGoal(), profile.getActivityLevel()
        );

        String recommendation = geminiService.getRecommendation(user.getGeminiApiKey(), prompt);
        return Map.of("content", recommendation);
    }

    @PostMapping("/meal-recommendation")
    public Map<String, String> getMealRecommendation(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        UserProfile profile = profileService.getProfileByUserId(user.getId());

        String prompt = String.format(
            "I am a %d year old %s, %d cm tall, weighing %.1f kg. My goal is to %s. " +
            "Please suggest a healthy meal plan for one day using local Pakistani foods. " +
            "Include breakfast, lunch, dinner, and a snack. Keep it concise.",
            profile.getAge(), profile.getGender(), profile.getHeight().intValue(), profile.getWeight(), profile.getGoal()
        );

        String recommendation = geminiService.getRecommendation(user.getGeminiApiKey(), prompt);
        return Map.of("content", recommendation);
    }
}
