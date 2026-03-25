package com.gestoracademico.gestoracademico.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorCreationDTO {
    private String userEmail;
    private String password;
    private String name;
    private String lastName;
    private String phone;
    private String dni;
    private String fileNumber;
}
