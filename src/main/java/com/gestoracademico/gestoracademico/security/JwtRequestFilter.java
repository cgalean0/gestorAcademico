package com.gestoracademico.gestoracademico.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    public JwtRequestFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtenemos el encabezado de autorización
        final String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        //Comprobar el formato del token (que sea Bearer)
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            // Intentamos validacion y extracción.
            if (jwtProvider.validateToken(token)) {
                try {
                    username = jwtProvider.getUsernameFromToken(token);
                    //Obtenemos el role del usuario
                    String role = jwtProvider.getRoleFromToken(token);
                    //Si el token es válido y no hay autenticación previa
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        // Cargamos la autoridad
                        List<GrantedAuthority> authorities = Collections.singletonList(
                                new SimpleGrantedAuthority("ROLE_" + role)
                        );
                        //Crear el Objeto de Autenticación
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                username, null, authorities
                        );
                        //Inyectar en el Contexto de Spring Security
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (Exception e) {
                    //Manejar cualquier error de extracción/parsing si el validateToken falló pero se intentó el parseo
                    logger.error("Error establishing authentication: " + e.getMessage());
                }
            }
        }
        //Continuar la cadena de filtros
        filterChain.doFilter(request, response);
    }
}
