package com.gestoracademico.gestoracademico.security;

import com.gestoracademico.gestoracademico.dto.AuthResponseDTO;
import com.gestoracademico.gestoracademico.model.User;

import javax.naming.AuthenticationException;

public interface IAuthService {
    AuthResponseDTO login(String userName, String plainPassword) throws AuthenticationException;
    void resetPassword(String userName, String newPassword);
}
