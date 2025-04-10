package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.entity.Trajet;
import com.covoiturage.covoiturage2.repository.TrajetRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trajets")
public class TrajetController {

    private final TrajetRepository trajetRepository;

    @Autowired
    public TrajetController(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    // 🚗 Seuls les conducteurs peuvent créer un trajet

    @PostMapping
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<Trajet> createTrajet(@RequestBody Trajet trajet) {
        Trajet savedTrajet = trajetRepository.save(trajet);
        return ResponseEntity.ok(savedTrajet); // ✅ Retourne un objet Trajet au lieu d'une String
    }

    // 🔍 Récupérer un trajet spécifique
    @GetMapping("/{id}")
    public ResponseEntity<Trajet> getTrajetById(@PathVariable Long id) {
        Optional<Trajet> trajet = trajetRepository.findById(id);
        return trajet.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build()); // ✅ Retourne 404 si non trouvé
    }

    // 🚗 Récupérer les trajets d'un conducteur

    @GetMapping("/conducteur/{id}")
    public ResponseEntity<List<Trajet>> getTrajetsByConducteur(@PathVariable Long id) {
        List<Trajet> trajets = trajetRepository.findByUserId(id);
        return ResponseEntity.ok(trajets);
    }

    // 🛠 Modifier un trajet (seulement pour le conducteur)

    @PutMapping("/{id}")
    public ResponseEntity<Trajet> updateTrajet(@PathVariable Long id, @RequestBody Trajet trajetDetails) {
        Optional<Trajet> optionalTrajet = trajetRepository.findById(id);

        if (optionalTrajet.isEmpty()) {
            return ResponseEntity.notFound().build(); // ✅ Retourne 404 si non trouvé
        }

        Trajet trajet = optionalTrajet.get();
        trajet.setStartLocation(trajetDetails.getStartLocation());
        trajet.setEndLocation(trajetDetails.getEndLocation());
        trajet.setDepartureTime(trajetDetails.getDepartureTime());
        trajet.setAvailableSeats(trajetDetails.getAvailableSeats());
        trajet.setAllowsLuggage(trajetDetails.isAllowsLuggage());
        trajet.setAllowsMusic(trajetDetails.isAllowsMusic());

        Trajet updatedTrajet = trajetRepository.save(trajet);
        return ResponseEntity.ok(updatedTrajet); // ✅ Retourne l'objet Trajet mis à jour
    }

    // ❌ Supprimer un trajet (seulement pour le conducteur)

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable Long id) {
        Optional<Trajet> optionalTrajet = trajetRepository.findById(id);

        if (optionalTrajet.isEmpty()) {
            return ResponseEntity.notFound().build(); // ✅ Retourne 404 si non trouvé
        }

        trajetRepository.delete(optionalTrajet.get());
        return ResponseEntity.noContent().build(); // ✅ Retourne 204 No Content après suppression
    }
}
