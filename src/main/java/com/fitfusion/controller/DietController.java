package com.fitfusion.controller;

import com.fitfusion.model.DietLog;
import com.fitfusion.model.FoodItem;
import com.fitfusion.model.User;
import com.fitfusion.service.DietService;
import com.fitfusion.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DietController {

    @Autowired
    private DietService dietService;

    @Autowired
    private UserService userService;

    @GetMapping("/diet")
    public String viewDiet(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<DietLog> todayLogs = dietService.getDailyDietLogs(user.getId(), LocalDate.now());
        List<FoodItem> allFoods = dietService.getAllFoodItems();

        // Calculate totals
        double totalCalories = todayLogs.stream().mapToDouble(l -> (l.getFoodItem().getCalories() * l.getQuantity()) / 100).sum();
        double totalProtein = todayLogs.stream().mapToDouble(l -> (l.getFoodItem().getProtein() * l.getQuantity()) / 100).sum();
        double totalCarbs = todayLogs.stream().mapToDouble(l -> (l.getFoodItem().getCarbs() * l.getQuantity()) / 100).sum();
        double totalFats = todayLogs.stream().mapToDouble(l -> (l.getFoodItem().getFats() * l.getQuantity()) / 100).sum();

        model.addAttribute("todayLogs", todayLogs);
        model.addAttribute("foods", allFoods);
        model.addAttribute("totalCalories", Math.round(totalCalories));
        model.addAttribute("totalProtein", Math.round(totalProtein));
        model.addAttribute("totalCarbs", Math.round(totalCarbs));
        model.addAttribute("totalFats", Math.round(totalFats));
        
        return "diet";
    }

    @PostMapping("/diet/log")
    public String logMeal(@AuthenticationPrincipal UserDetails userDetails,
                          @RequestParam Long foodItemId,
                          @RequestParam Double quantity,
                          @RequestParam String mealType) {
        User user = userService.findByUsername(userDetails.getUsername());
        DietLog log = new DietLog();
        log.setUser(user);
        log.setDate(LocalDate.now());
        log.setFoodItem(dietService.getFoodItemById(foodItemId).orElseThrow());
        log.setQuantity(quantity);
        log.setMealType(mealType);
        
        dietService.logDiet(log);
        return "redirect:/diet";
    }
    
    @PostMapping("/diet/delete")
    public String deleteLog(@RequestParam Long id) {
        dietService.deleteDietLog(id);
        return "redirect:/diet";
    }
}
