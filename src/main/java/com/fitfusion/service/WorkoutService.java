package com.fitfusion.service;

import com.fitfusion.model.*;
import com.fitfusion.repository.WorkoutLogRepository;
import com.fitfusion.repository.WorkoutPlanItemRepository;
import com.fitfusion.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    private WorkoutPlanItemRepository workoutPlanItemRepository;

    @Autowired
    private WorkoutLogRepository workoutLogRepository;

    // --- Plans ---

    public List<WorkoutPlan> getUserWorkoutPlans(Long userId) {
        return workoutPlanRepository.findByUserId(userId);
    }

    public WorkoutPlan getWorkoutPlanById(Long id) {
        return workoutPlanRepository.findById(id).orElse(null);
    }

    @Transactional
    public WorkoutPlan createWorkoutPlan(WorkoutPlan plan, List<WorkoutPlanItem> items) {
        WorkoutPlan savedPlan = workoutPlanRepository.save(plan);
        for (WorkoutPlanItem item : items) {
            item.setWorkoutPlan(savedPlan);
            workoutPlanItemRepository.save(item);
        }
        return savedPlan;
    }
    
    public List<WorkoutPlanItem> getPlanItems(Long planId) {
        return workoutPlanItemRepository.findByWorkoutPlanId(planId);
    }

    // --- Logs ---

    public WorkoutLog logWorkout(WorkoutLog log) {
        return workoutLogRepository.save(log);
    }

    public List<WorkoutLog> getWorkoutLogs(Long userId) {
        return workoutLogRepository.findByUserId(userId);
    }

    public List<WorkoutLog> getDailyWorkoutLogs(Long userId, LocalDate date) {
        return workoutLogRepository.findByUserIdAndDate(userId, date);
    }
}
