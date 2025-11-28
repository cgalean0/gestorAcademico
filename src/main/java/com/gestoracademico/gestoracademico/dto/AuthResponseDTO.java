package com.gestoracademico.gestoracademico.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String userEmail;
    private String type = "bearer";
    public AuthResponseDTO(String token, String userEmail) {
        this.token = token;
        this.userEmail = userEmail;
    }
}
