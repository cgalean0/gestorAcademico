package com.gestoracademico.gestoracademico.security;

import com.gestoracademico.gestoracademico.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration.ms}")
    private Long jwtExpirationMs;

    private final JwtParser jwtParser;

    public JwtProvider(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        Map<String, Object> domainClaims = new HashMap<>();
        domainClaims.put("userId", user.getId());
        domainClaims.put("role", user.getRole().name());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .claims(domainClaims)
                .subject(user.getUserName())
                .issuedAt(now)
                .expiration(expirationDate)
                .issuer("AdminGestor")
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        }catch (SignatureException e) {
            System.err.println("Firma JWT inválida: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Token JWT expirado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Token JWT mal formado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Token JWT no soportado: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Claims JWT vacío: " + e.getMessage());
        }
        return false;
    }

    public Claims getClaimsFromToken(String token) {
        return jwtParser.parseClaimsJwt(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return getClaimsFromToken(token).get("role", String.class);
    }
}
