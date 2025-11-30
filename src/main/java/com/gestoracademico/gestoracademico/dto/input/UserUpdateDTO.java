package com.gestoracademico.gestoracademico.dto.input;

import com.gestoracademico.gestoracademico.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String name;
    private String lastName;
    private String userEmail;
    private String phone;
    private String dni;
    private Role role;
}
