package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.input.SubjectCreationDTO;
import com.gestoracademico.gestoracademico.dto.output.SubjectDTO;
import com.gestoracademico.gestoracademico.model.Subject;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface SubjectMapper {
    SubjectDTO toDTO (Subject entity);
    Subject toEntity(SubjectCreationDTO dto);
}
