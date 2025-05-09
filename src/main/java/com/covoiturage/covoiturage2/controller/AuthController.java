package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.entity.Role;
import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.repository.UserRepository;
import com.covoiturage.covoiturage2.security.JwtResponse;
import com.covoiturage.covoiturage2.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        if (user.getUserType() == null) {
            user.setUserType(Role.PASSAGER); // Rôle par défaut
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                // Ajout du rôle de l'utilisateur (user.getUserType()) au token
                String token = jwtUtil.generateToken(email, user.getUserType().name(),user.getId());
                return ResponseEntity.ok(new JwtResponse(token));  // Retourne un token JWT
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }




}

