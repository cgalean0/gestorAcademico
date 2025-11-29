package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.SubjectUpdateDTO;
import com.gestoracademico.gestoracademico.model.Subject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper (componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SubjectUpdateMapper {
    @Mapping(target = "idSubject", ignore = true)
    void updateEntityToDto(SubjectUpdateDTO dto, @MappingTarget Subject entity);
}
