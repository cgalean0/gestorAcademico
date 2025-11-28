package com.gestoracademico.gestoracademico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorCreationDTO {
    private String email;
    private String password;
    private String name;
    private String lastName;
    private String phone;
    private String dni;
    private String fileNumber;
}
