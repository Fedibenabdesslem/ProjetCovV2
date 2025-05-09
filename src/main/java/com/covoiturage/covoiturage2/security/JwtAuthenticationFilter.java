package com.covoiturage.covoiturage2.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println(request);

        String token = extractTokenFromRequest(request);

        System.out.println(token);

        // Vérification du token
        if (StringUtils.hasText(token)) {
            try {
                String email = jwtUtil.extractEmail(token);
                System.out.println( " email: " + email);
                String role = jwtUtil.extractRole(token);

                // Vérification de la validité du token
                if (jwtUtil.validateToken(token)) {
                    // Logique d'authentification
                    System.out.println("Token valid, role: " + role + " email: " + email);
                    UsernamePasswordAuthenticationToken authentication = createAuthentication(email, role);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("Invalid token.");
                }
            } catch (Exception e) {
                // Log de l'erreur en cas d'exception
                System.out.println("Error while extracting or validating token: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token is invalid or expired.");
                return;
            }
        } else {
            System.out.println("Token missing in request.");
        }

        filterChain.doFilter(request, response);
    }

    // Extraire le token Bearer de la requête HTTP
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Supprimer "Bearer " du début
        }
        return null;
    }

    // Créer l'authentification avec le rôle de l'utilisateur
    private UsernamePasswordAuthenticationToken createAuthentication(String email, String role) {
        // Ajouter "ROLE_" devant le rôle pour correspondre à la convention Spring Security
        String authority = "ROLE_" + role;
        return new UsernamePasswordAuthenticationToken(email, null,
                Collections.singletonList(new SimpleGrantedAuthority(authority)));
    }
}
