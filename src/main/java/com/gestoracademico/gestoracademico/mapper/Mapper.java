package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.ProfessorCreationDTO;
import com.gestoracademico.gestoracademico.dto.ProfessorDTO;
import com.gestoracademico.gestoracademico.dto.UserCreationDTO;
import com.gestoracademico.gestoracademico.dto.UserDTO;
import com.gestoracademico.gestoracademico.model.User;

public class Mapper {
    public static UserDTO toDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getIdUser())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .phone(entity.getPhone())
                .userEmail(entity.getUserEmail())
                .role(entity.getRole())
                .fileNumber(entity.getFileNumber())
                .typeStudent(entity.getTypeStudent())
                .build();
    }

    public static User toEntity(UserCreationDTO dto) {
        return User.builder()
                .userEmail(dto.getUserEmail())
                .build();
    }

    public static User toEntity(ProfessorCreationDTO dto) {
        return User.builder()
                .userEmail(dto.getEmail())
                .dni(dto.getDni())
                .name(dto.getName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .fileNumber(dto.getFileNumber())
                .build();
    }
}
