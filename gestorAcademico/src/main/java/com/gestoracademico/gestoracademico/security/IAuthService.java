package com.gestoracademico.gestoracademico.security;

import com.gestoracademico.gestoracademico.dto.output.AuthResponseInternalEvent;

import javax.naming.AuthenticationException;

public interface IAuthService {
    AuthResponseInternalEvent login(String userName, String plainPassword) throws AuthenticationException;
    void resetPassword(String userName, String newPassword);
}
