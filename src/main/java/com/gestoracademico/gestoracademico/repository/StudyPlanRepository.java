package com.gestoracademico.gestoracademico.repository;

import com.gestoracademico.gestoracademico.model.StudyPlan;
import com.gestoracademico.gestoracademico.model.StudyPlanId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudyPlanRepository extends JpaRepository<StudyPlan, StudyPlanId> {
    List<StudyPlan> findById_CareerId(Long careerId);
}
