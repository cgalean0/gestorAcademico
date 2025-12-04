package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.output.ResponsibleSubjectDTO;
import com.gestoracademico.gestoracademico.model.ResponsibleSubject;
import com.gestoracademico.gestoracademico.model.Subject;
import com.gestoracademico.gestoracademico.model.User;
import com.gestoracademico.gestoracademico.repository.SubjectRepository;
import com.gestoracademico.gestoracademico.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ResponsibleSubjectMapper {
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private UserRepository userRepository;

    @Mapping(source = "id.professorId", target = "professorId")
    @Mapping(source = "id.subjectId", target = "subjectId")
    @Mapping(source = "professor.name", target = "professorName")
    @Mapping(source = "subject.name", target = "subjectName")
    public abstract ResponsibleSubjectDTO toDTO(ResponsibleSubject responsibleSubject);

    @Mapping(source = "professorId", target = "id.professorId")
    @Mapping(source = "subjectId", target = "id.subjectId")
    @Mapping(source = "professorId", target = "professor", qualifiedByName = "mapProfessor")
    @Mapping(source = "subjectId", target = "subject", qualifiedByName = "mapSubject")
    public abstract ResponsibleSubject toEntity(ResponsibleSubjectDTO dto);


    @Named("mapSubject")
    public Subject mapSubject(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found with ID: " + subjectId));
    }

    @Named("mapProfessor")
    public User mapProfessor(Long professorId) {
        return userRepository.findById(professorId)
                .orElseThrow(() -> new RuntimeException("Professor not found with ID: " + professorId));
    }
}
