package com.gestoracademico.gestoracademico.mapper;

import com.gestoracademico.gestoracademico.dto.UserCreationDTO;
import com.gestoracademico.gestoracademico.dto.UserDTO;
import com.gestoracademico.gestoracademico.model.User;

public class Mapper {
    public static UserDTO toDTO(User entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .role(entity.getRole())
                .build();
    }

    public static User toEntity(UserDTO dto) {
        return User.builder()
                .userName(dto.getUserName())
                .role(dto.getRole())
                .build();
    }

    public static User toEntity(UserCreationDTO dto) {
        return User.builder()
                .userName(dto.getUserName())
                .build();
    }
}
