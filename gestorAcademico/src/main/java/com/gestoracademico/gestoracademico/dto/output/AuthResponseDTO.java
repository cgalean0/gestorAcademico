package com.gestoracademico.gestoracademico.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AuthResponseDTO {
    private String userName;
    private String role;
    public AuthResponseDTO(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }
}
