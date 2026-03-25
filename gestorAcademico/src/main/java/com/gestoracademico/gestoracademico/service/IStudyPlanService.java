package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.output.StudyPlanDTO;
import java.util.List;

public interface IStudyPlanService {
    StudyPlanDTO addSubjectToPlan(Long careerId, Long subjectId, Integer year);
    void removeSubjectToPlan(Long careerId, Long subjectId);
    List<StudyPlanDTO> getSubjectsByCareer(Long careerId);
}
