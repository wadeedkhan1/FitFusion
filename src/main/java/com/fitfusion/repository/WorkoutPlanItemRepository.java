package com.fitfusion.repository;

import com.fitfusion.model.WorkoutPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WorkoutPlanItemRepository extends JpaRepository<WorkoutPlanItem, Long> {
    List<WorkoutPlanItem> findByWorkoutPlanId(Long planId);
}
