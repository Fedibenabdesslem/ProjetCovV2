package com.covoiturage.covoiturage2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/auth/signup", "/auth/signin").permitAll() // Permet l'accès à signup et signin sans authentification
                        .requestMatchers(HttpMethod.POST, "/reservations").hasRole("PASSAGER")// Utiliser hasAuthority au lieu de hasRole
                        .requestMatchers(HttpMethod.GET, "/reservations/passager/{passengerId}").hasAuthority("ROLE_PASSAGER")
                        .requestMatchers(HttpMethod.PUT, "/reservations/{reservationId}/status").hasAnyAuthority("ROLE_CONDUCTEUR", "ROLE_ADMIN")

                        .requestMatchers("/profile/update").hasAnyRole("CONDUCTEUR", "PASSAGER", "ADMIN")  // Utiliser hasAuthority

                        .anyRequest().authenticated()) // Requiert une authentification pour les autres requêtes
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ajouter le filtre JWT avant le filtre UsernamePasswordAuthenticationFilter
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utiliser BCrypt pour encoder les mots de passe
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService); // Utiliser CustomUserDetailsService
        provider.setPasswordEncoder(passwordEncoder()); // Utiliser BCryptPasswordEncoder
        return new ProviderManager(provider); // Retourner un AuthenticationManager configuré
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil); // Définir JwtAuthenticationFilter comme un bean
    }
}
