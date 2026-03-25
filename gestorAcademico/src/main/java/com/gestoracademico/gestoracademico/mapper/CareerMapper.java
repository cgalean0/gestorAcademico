package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.input.CareerCreationDTO;
import com.gestoracademico.gestoracademico.dto.output.CareerDTO;
import com.gestoracademico.gestoracademico.model.Career;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface CareerMapper {
    CareerDTO toDTO(Career career);
    Career toEntity(CareerCreationDTO dto);
}
