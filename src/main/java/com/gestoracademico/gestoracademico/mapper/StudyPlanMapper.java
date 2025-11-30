package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.input.StudyPlanCreationDTO;
import com.gestoracademico.gestoracademico.dto.output.StudyPlanDTO;
import com.gestoracademico.gestoracademico.model.Career;
import com.gestoracademico.gestoracademico.model.StudyPlan;
import com.gestoracademico.gestoracademico.model.Subject;
import com.gestoracademico.gestoracademico.repository.CareerRepository;
import com.gestoracademico.gestoracademico.repository.SubjectRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper (componentModel = "spring")
public abstract class StudyPlanMapper {
    @Autowired
    CareerRepository careerRepository;
    @Autowired
    SubjectRepository subjectRepository;

    @Mapping(source = "id.careerId", target = "careerId")
    @Mapping(source = "id.subjectId", target = "subjectId")
    @Mapping(source = "career.name", target = "careerName")
    @Mapping(source = "subject.name", target = "subjectName")
    public abstract StudyPlanDTO toDTO(StudyPlan entity);

    @Mapping(source = "careerId", target = "id.careerId")
    @Mapping(source = "subjectId", target = "id.subjectId")
    @Mapping(source = "careerId", target = "career", qualifiedByName = "mapCareer")
    @Mapping(source = "subjectId", target = "subject", qualifiedByName = "mapSubject")
    public abstract StudyPlan toEntity(StudyPlanCreationDTO dto);


    @Named("mapCareer")
    public Career mapCareer(Long careerId) {
        return careerRepository.findById(careerId)
                .orElseThrow(() -> new RuntimeException("Career not found with ID: " + careerId));
    }
    @Named("mapSubject")
    public Subject mapSubject(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));
    }
}
