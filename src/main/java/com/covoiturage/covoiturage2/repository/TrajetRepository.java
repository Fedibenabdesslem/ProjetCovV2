package com.covoiturage.covoiturage2.repository;

import com.covoiturage.covoiturage2.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    List<Trajet> findByUserId(Long userId); // Trouver les trajets d'un conducteur spécifique
}
