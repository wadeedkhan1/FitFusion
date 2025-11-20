package com.fitfusion.repository;

import com.fitfusion.model.DietLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface DietLogRepository extends JpaRepository<DietLog, Long> {
    List<DietLog> findByUserIdAndDate(Long userId, LocalDate date);
}
