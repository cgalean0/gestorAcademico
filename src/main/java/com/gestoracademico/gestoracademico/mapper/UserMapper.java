package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.input.ProfessorCreationDTO;
import com.gestoracademico.gestoracademico.dto.input.StudentCreationDTO;
import com.gestoracademico.gestoracademico.dto.input.UserCreationDTO;
import com.gestoracademico.gestoracademico.dto.output.UserDTO;
import com.gestoracademico.gestoracademico.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface UserMapper {
    /// Mapeo a DTO
    UserDTO toDTO(User entity);
    /// Mapeo de creación genérica
    /// Ignoramos password para no copiar el texto plano del DTO al Entity
    @Mapping(target = "password", ignore = true)
    User toEntity(UserCreationDTO dto);

    /// Mapeo de creacion de professor
    /// Role y password son asignados en el service
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(ProfessorCreationDTO dto);

    /// Mapeo de creación de Student
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toEntity(StudentCreationDTO dto);
}
