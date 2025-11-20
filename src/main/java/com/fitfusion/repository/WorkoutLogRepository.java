package com.fitfusion.repository;

import com.fitfusion.model.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Long> {
    List<WorkoutLog> findByUserIdAndDate(Long userId, LocalDate date);
    List<WorkoutLog> findByUserId(Long userId);
}
