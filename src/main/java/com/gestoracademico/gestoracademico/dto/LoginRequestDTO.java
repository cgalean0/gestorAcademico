package com.gestoracademico.gestoracademico.dto;

import com.gestoracademico.gestoracademico.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    private String userEmail;
    private String password;
}
