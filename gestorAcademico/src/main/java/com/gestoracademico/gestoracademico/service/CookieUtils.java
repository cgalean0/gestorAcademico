package com.gestoracademico.gestoracademico.service;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CookieUtils {
    public String extractTokenOfCookie(HttpServletRequest req) {
        if (req.getCookies() == null) return null;

        return Arrays.stream(req.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);
    }
}
