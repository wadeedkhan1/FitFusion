package com.fitfusion.controller;

import com.fitfusion.model.User;
import com.fitfusion.model.UserProfile;
import com.fitfusion.service.ProfileService;
import com.fitfusion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String viewProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        UserProfile profile = profileService.getProfileByUserId(user.getId());
        
        if (profile == null) {
            return "redirect:/profile/setup";
        }
        
        model.addAttribute("user", user);
        model.addAttribute("profile", profile);
        return "profile";
    }

    @GetMapping("/profile/setup")
    public String setupProfileForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        UserProfile profile = profileService.getProfileByUserId(user.getId());
        
        if (profile == null) {
            profile = new UserProfile();
            profile.setUser(user);
        }
        
        model.addAttribute("profile", profile);
        return "profile-setup";
    }

    @PostMapping("/profile/setup")
    public String saveProfile(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute UserProfile profile) {
        User user = userService.findByUsername(userDetails.getUsername());
        profile.setUser(user); // Ensure the user is set
        // If updating, we might need to fetch the existing ID, but for now let's assume this handles both
        // Actually, for update, we should probably fetch existing to keep ID
        UserProfile existing = profileService.getProfileByUserId(user.getId());
        if (existing != null) {
            profile.setId(existing.getId());
        }
        
        profileService.saveOrUpdateProfile(profile);
        return "redirect:/profile";
    }
}
