package com.gestoracademico.gestoracademico.dto;

import com.gestoracademico.gestoracademico.enums.Role;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String userName;
    private Role role;
}
