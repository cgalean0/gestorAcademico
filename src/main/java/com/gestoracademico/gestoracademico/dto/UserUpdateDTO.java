package com.gestoracademico.gestoracademico.dto;

import com.gestoracademico.gestoracademico.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    private String userName;
    private Role role;
}
