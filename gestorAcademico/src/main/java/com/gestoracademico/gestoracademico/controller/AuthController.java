package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.output.AuthResponseDTO;
import com.gestoracademico.gestoracademico.dto.output.AuthResponseInternalEvent;
import com.gestoracademico.gestoracademico.dto.input.LoginRequest;
import com.gestoracademico.gestoracademico.security.IAuthService;
import com.gestoracademico.gestoracademico.security.JwtProvider;
import com.gestoracademico.gestoracademico.service.CookieUtils;
import com.gestoracademico.gestoracademico.service.RedisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints de autenticación")
public class AuthController {

    private final IAuthService authService;
    private final RedisService redisService;
    private final JwtProvider jwtProvider;
    private final CookieUtils cookieUtils;
    public AuthController(IAuthService authService, RedisService redisService, JwtProvider jwtProvider, CookieUtils cookieUtils) {
        this.authService = authService;
        this.redisService = redisService;
        this.jwtProvider = jwtProvider;
        this.cookieUtils = cookieUtils;
    }

 
    @Value("${app.cookie.secure:false}")  // false por defecto, configurable
    private boolean secureCookie;
    @Value("${app.cookie.sameSite:Lax}")
    private String sameSiteType;
    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario y retorna un token JWT para acceder a los endpoints protegidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequest request) {
        try {
            AuthResponseInternalEvent internalResult = authService.login(request.userName(), request.password());
            // Create a cookie 
            ResponseCookie cookie = ResponseCookie.from("token", internalResult.token())
                    .httpOnly(true)
                    .secure(secureCookie)
                    .path("/")
                    .sameSite(sameSiteType)
                    .build();

            AuthResponseDTO response = new AuthResponseDTO(internalResult.userName(), internalResult.userRole().toString());

            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Cerrar sesión", description = "Deshabilita el token asignado al usuario.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = cookieUtils.extractTokenOfCookie(request);
        long expirationTime = jwtProvider.getExpirationToken(token);
        long ttlMilis = expirationTime - System.currentTimeMillis();
        redisService.banToken(token, ttlMilis);

        // Limpiar la cookie
        ResponseCookie cookie = ResponseCookie.from("token", "")
        .httpOnly(true)
        .secure(secureCookie)
        .path("/")
        .maxAge(0)
        .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }
}
