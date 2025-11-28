package com.gestoracademico.gestoracademico.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationDTO {
    private String userEmail;
    private String password;
}
