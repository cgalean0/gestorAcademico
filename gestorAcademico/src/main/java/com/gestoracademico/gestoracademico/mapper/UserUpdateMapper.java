package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.input.ProfessorUpdateDTO;
import com.gestoracademico.gestoracademico.dto.input.StudentUpdateDTO;
import com.gestoracademico.gestoracademico.dto.input.UserUpdateDTO;
import com.gestoracademico.gestoracademico.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateMapper {
    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityToDto(UserUpdateDTO dto, @MappingTarget User entity);

    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityToDto(ProfessorUpdateDTO dto, @MappingTarget User entity);

    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityToDto(StudentUpdateDTO dto, @MappingTarget User entity);
}
