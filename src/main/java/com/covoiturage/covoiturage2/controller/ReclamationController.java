package com.covoiturage.covoiturage2.controller;


import com.covoiturage.covoiturage2.entity.Reclamation;
import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.repository.UserRepository;
import com.covoiturage.covoiturage2.service.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reclamations")
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Créer une réclamation (conducteur uniquement)
    @PostMapping
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<?> createReclamation(
            @RequestParam Long conducteurId,
            @RequestParam String nomConducteur,
            @RequestParam String nomPassager,
            @RequestParam String numeroTelephonePassager,
            @RequestParam String description) {

        Optional<User> conducteur = userRepository.findById(conducteurId);
        if (conducteur.isEmpty()) {
            return ResponseEntity.badRequest().body("Conducteur introuvable.");
        }

        Reclamation reclamation = new Reclamation();
        reclamation.setUser(conducteur.get());
        reclamation.setNomConducteur(nomConducteur);
        reclamation.setNomPassager(nomPassager);
        reclamation.setNumeroTelephonePassager(numeroTelephonePassager);
        reclamation.setDescription(description);

        return ResponseEntity.ok(reclamationService.save(reclamation));
    }

    // ✅ Récupérer toutes les réclamations (admin)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reclamation>> getAll() {
        return ResponseEntity.ok(reclamationService.findAll());
    }

    // ✅ Mettre à jour le statut (admin)
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(reclamationService.updateStatus(id, status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Statut invalide.");
        }
    }
}
