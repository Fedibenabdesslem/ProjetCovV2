package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.dto.ReclamationRequest;
import com.covoiturage.covoiturage2.dto.UpdateStatusRequest;
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
    public ResponseEntity<?> createReclamation(@RequestBody ReclamationRequest request) {
        Optional<User> conducteur = userRepository.findById(request.getConducteurId());
        if (conducteur.isEmpty()) {
            return ResponseEntity.badRequest().body("Conducteur introuvable.");
        }

        Reclamation reclamation = new Reclamation();
        reclamation.setUser(conducteur.get());
        reclamation.setNomConducteur(request.getNomConducteur());
        reclamation.setNomPassager(request.getNomPassager());
        reclamation.setNumeroTelephonePassager(request.getNumeroTelephonePassager());
        reclamation.setDescription(request.getDescription());

        return ResponseEntity.ok(reclamationService.save(reclamation));
    }

    // ✅ Récupérer toutes les réclamations (admin uniquement)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reclamation>> getAll() {
        return ResponseEntity.ok(reclamationService.findAll());
    }

    // ✅ Mettre à jour le statut d’une réclamation (admin uniquement)
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        try {
            return ResponseEntity.ok(reclamationService.updateStatus(id, request.getStatus().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Statut invalide.");
        }
    }
}
