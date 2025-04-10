package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.repository.UserRepository;
import com.covoiturage.covoiturage2.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    public ProfileController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProfile(@RequestBody User user, @RequestHeader("Authorization") String token) {
        // Extraire l'email de l'utilisateur à partir du token JWT
        String email = jwtUtil.extractEmail(token.substring(7));  // Extraire l'email du token

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();

            // Mettre à jour les informations du profil
            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            currentUser.setPhoneNumber(user.getPhoneNumber());
            currentUser.setProfilePicture(user.getProfilePicture());

            // Si l'utilisateur change son mot de passe, l'encoder
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            userRepository.save(currentUser);
            return ResponseEntity.ok("Profile updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}

