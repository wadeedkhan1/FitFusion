package com.fitfusion.controller;

import com.fitfusion.model.User;
import com.fitfusion.repository.UserRepository;
import com.fitfusion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SettingsController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/settings")
    public String viewSettings(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("apiKey", user.getGeminiApiKey());
        return "settings";
    }

    @PostMapping("/settings/api-key")
    public String saveApiKey(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String apiKey) {
        User user = userService.findByUsername(userDetails.getUsername());
        user.setGeminiApiKey(apiKey);
        userRepository.save(user);
        return "redirect:/settings?success";
    }
}
