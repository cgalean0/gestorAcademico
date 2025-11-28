package com.gestoracademico.gestoracademico.dto;

import com.gestoracademico.gestoracademico.enums.Role;
import com.gestoracademico.gestoracademico.enums.TypeStudent;
import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String lastName;
    private String userEmail;
    private String phone;
    private Role role;
    private String fileNumber;
    private TypeStudent typeStudent;
}
