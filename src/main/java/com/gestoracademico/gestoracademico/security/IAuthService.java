package com.gestoracademico.gestoracademico.security;

import com.gestoracademico.gestoracademico.dto.output.AuthResponseDTO;

import javax.naming.AuthenticationException;

public interface IAuthService {
    AuthResponseDTO login(String userName, String plainPassword) throws AuthenticationException;
    void resetPassword(String userName, String newPassword);
}
