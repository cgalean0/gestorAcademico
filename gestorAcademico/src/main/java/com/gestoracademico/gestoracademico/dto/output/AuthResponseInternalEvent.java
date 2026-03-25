package com.gestoracademico.gestoracademico.dto.output;

import com.gestoracademico.gestoracademico.enums.Role;

public record AuthResponseInternalEvent(
    String token,
    String userName,
    String type,
    Role userRole
) {

}
