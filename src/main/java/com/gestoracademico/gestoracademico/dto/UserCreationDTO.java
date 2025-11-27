package com.gestoracademico.gestoracademico.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationDTO {
    private String userName;
    private String password;
}
