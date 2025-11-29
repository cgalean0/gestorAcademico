package com.gestoracademico.gestoracademico.dto;

import com.gestoracademico.gestoracademico.enums.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfessorDTO {
    private Long idUser;
    private String email;
    private String name;
    private String lastName;
    private String phone;
    private String dni;
    private String fileNumber;
}
