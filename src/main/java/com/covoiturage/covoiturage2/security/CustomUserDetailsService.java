package com.covoiturage.covoiturage2.security;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.covoiturage.covoiturage2.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)  // Recherche par email
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())  // Utilise email comme username
                        .password(user.getPassword())
                        .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))  // Récupère les rôles
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }
}
