package com.fitfusion.service;

import com.fitfusion.model.DietLog;
import com.fitfusion.model.FoodItem;
import com.fitfusion.repository.DietLogRepository;
import com.fitfusion.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DietService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private DietLogRepository dietLogRepository;

    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    public List<FoodItem> searchFoodItems(String query) {
        return foodItemRepository.findByNameContainingIgnoreCase(query);
    }
    
    public Optional<FoodItem> getFoodItemById(Long id) {
        return foodItemRepository.findById(id);
    }

    public FoodItem saveFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    public DietLog logDiet(DietLog log) {
        return dietLogRepository.save(log);
    }

    public List<DietLog> getDailyDietLogs(Long userId, LocalDate date) {
        return dietLogRepository.findByUserIdAndDate(userId, date);
    }

    public void deleteDietLog(Long id) {
        dietLogRepository.deleteById(id);
    }
}
