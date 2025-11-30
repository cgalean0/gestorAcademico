package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.output.StudyPlanDTO;
import com.gestoracademico.gestoracademico.exceptions.CareerNotFounException;
import com.gestoracademico.gestoracademico.exceptions.SubjectNotFoundException;
import com.gestoracademico.gestoracademico.mapper.StudyPlanMapper;
import com.gestoracademico.gestoracademico.model.Career;
import com.gestoracademico.gestoracademico.model.StudyPlan;
import com.gestoracademico.gestoracademico.model.StudyPlanId;
import com.gestoracademico.gestoracademico.model.Subject;
import com.gestoracademico.gestoracademico.repository.CareerRepository;
import com.gestoracademico.gestoracademico.repository.StudyPlanRepository;
import com.gestoracademico.gestoracademico.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudyPlanService implements IStudyPlanService{
    private final SubjectRepository subjectRepository;
    private final CareerRepository careerRepository;
    private final StudyPlanRepository studyPlanRepository;
    private final StudyPlanMapper studyPlanMapper;

    public StudyPlanService(SubjectRepository subjectRepository, CareerRepository careerRepository, StudyPlanRepository studyPlanRepository, StudyPlanMapper studyPlanMapper) {
        this.subjectRepository = subjectRepository;
        this.careerRepository = careerRepository;
        this.studyPlanRepository = studyPlanRepository;
        this.studyPlanMapper = studyPlanMapper;
    }

    @Override
    public StudyPlanDTO addSubjectToPlan(Long careerId, Long subjectId, Integer year) {
        Career career = careerRepository.findById(careerId)
                .orElseThrow(() -> new CareerNotFounException(""));

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new SubjectNotFoundException(""));

        StudyPlanId planId = new StudyPlanId(careerId, subjectId);

        if (studyPlanRepository.existsById(planId))
            throw new RuntimeException("Subject is already registered for this study plan.");

        StudyPlan studyPlan = new StudyPlan();
        studyPlan.setId(planId);
        studyPlan.setSubject(subject);
        studyPlan.setCareer(career);
        studyPlan.setYear(year);

        StudyPlan savedStudyPlan = studyPlanRepository.save(studyPlan);
        return studyPlanMapper.toDTO(savedStudyPlan);
    }

    @Override
    public void removeSubjectToPlan(Long careerId, Long subjectId) {
        StudyPlanId planId = new StudyPlanId(careerId, subjectId);
        if (!studyPlanRepository.existsById(planId))
            throw new RuntimeException("Study plan relationship not found.");
        studyPlanRepository.deleteById(planId);
    }
    @Override
    public List<StudyPlanDTO> getSubjectsByCareer(Long careerId) {
        List<StudyPlan> planEntities = studyPlanRepository.findById_CareerId(careerId);
        return planEntities.stream()
                .map(studyPlanMapper::toDTO).toList();
    }
}
