package com.gestoracademico.gestoracademico.dto.input;

import com.gestoracademico.gestoracademico.enums.TypeStudent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentUpdateDTO {
    private String userEmail;
    private String name;
    private String lastName;
    private String phone;
    private String dni;
    private TypeStudent typeStudent;
}