package com.covoiturage.covoiturage2.security;

import jakarta.servlet.FilterChain; // Assure-toi d'importer FilterChain correctement
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractTokenFromRequest(request);
        if (token != null && jwtUtil.validateToken(token, getEmailFromToken(token))) {
            SecurityContextHolder.getContext().setAuthentication(createAuthentication(token));
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private String getEmailFromToken(String token) {
        return jwtUtil.extractEmail(token);
    }

    private UsernamePasswordAuthenticationToken createAuthentication(String token) {
        String email = jwtUtil.extractEmail(token);
        return new UsernamePasswordAuthenticationToken(email, null, List.of());
    }
}
