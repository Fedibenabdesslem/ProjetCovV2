package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.dto.RegisterDto;
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
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")

public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Dans ton AuthController ou UserController
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterDto dto) {
        try {
            // Vérifie si un utilisateur avec cet email existe déjà
            Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
            }

            // Création du nouvel utilisateur
            User newUser = new User();
            newUser.setFirstName(dto.getFirstName());
            newUser.setLastName(dto.getLastName());
            newUser.setEmail(dto.getEmail());
            newUser.setPhoneNumber(dto.getPhoneNumber());
            newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
            newUser.setUserType(dto.getUserType() != null ? dto.getUserType() : Role.PASSAGER);

            userRepository.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(true);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de l'inscription : " + e.getMessage());
        }
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

