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

@CrossOrigin(origins = "http://localhost:4200")
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
        String email = jwtUtil.extractEmail(token.substring(7));
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();

            currentUser.setFirstName(user.getFirstName());
            currentUser.setLastName(user.getLastName());
            currentUser.setPhoneNumber(user.getPhoneNumber());
            currentUser.setProfilePicture(user.getProfilePicture());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            userRepository.save(currentUser);
            return ResponseEntity.ok("Profile updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    // ✅ Nouvelle méthode : récupération d’un utilisateur par ID
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
