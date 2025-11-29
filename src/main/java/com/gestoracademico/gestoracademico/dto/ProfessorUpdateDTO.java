package com.gestoracademico.gestoracademico.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessorUpdateDTO {
    private String idUser;
    private String userEmail;
    private String name;
    private String lastName;
    private String dni;
    private String phone;
    private String fileNumber;
}
