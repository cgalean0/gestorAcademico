package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.input.CareerUpdateDTO;
import com.gestoracademico.gestoracademico.model.Career;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper (componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CareerUpdateMapper {
    @Mapping(target = "idCareer", ignore = true)
    void updateEntityToDto(CareerUpdateDTO dto, @MappingTarget Career entity);
}
