package com.gestoracademico.gestoracademico.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String userName;
    private String type = "bearer";
    public AuthResponseDTO(String token, String userName) {
        this.token = token;
        this.userName = userName;
    }
}
