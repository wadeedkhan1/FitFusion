package com.fitfusion.repository;

import com.fitfusion.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
    Optional<MuscleGroup> findByName(String name);
}
