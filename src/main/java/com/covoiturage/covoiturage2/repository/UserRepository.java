package com.covoiturage.covoiturage2.repository;

import com.covoiturage.covoiturage2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // Recherche par email

    Optional<User> findById(Long id);  // Recherche par ID, déjà défini, pas besoin de modification
}
