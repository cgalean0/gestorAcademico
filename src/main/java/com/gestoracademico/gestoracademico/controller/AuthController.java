package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.AuthResponseDTO;
import com.gestoracademico.gestoracademico.dto.LoginRequestDTO;
import com.gestoracademico.gestoracademico.security.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;
    // Constructor and dependencies injection
    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    /**
     * Login end point
     * @param request contains the data for the check login
     * */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        try {
            AuthResponseDTO response = authService.login(request.getUserEmail(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
