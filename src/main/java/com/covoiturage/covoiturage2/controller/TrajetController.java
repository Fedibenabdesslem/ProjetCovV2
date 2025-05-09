package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.entity.Trajet;
import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.repository.TrajetRepository;
import com.covoiturage.covoiturage2.repository.UserRepository;
import com.covoiturage.covoiturage2.security.JwtUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trajets")
public class TrajetController {




    private final TrajetRepository trajetRepository;
    @Autowired
private JwtUtil jwtUtil ;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    public TrajetController(TrajetRepository trajetRepository) {
        this.trajetRepository = trajetRepository;
    }

    // 🚗 Seuls les conducteurs peuvent créer un trajet

    @PostMapping
    //@PreAuthorize("hasRole('CONDUCTEUR')")

    public ResponseEntity<Trajet> createTrajet(@RequestBody Trajet trajet,
                                               @RequestHeader("Authorization") String authHeader) {
        // ✅ Extraire le token (en retirant "Bearer ")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build(); // Unauthorized si le token est mal formé
        }

        String token = authHeader.substring(7); // Retire "Bearer "

        try {
            Long userId = jwtUtil.extractUserId(token);
            Optional<User> user = userRepository.findById(userId);

            if (user == null) {
                return ResponseEntity.status(404).build(); // Utilisateur introuvable
            }

            trajet.setUser(user.get()); // Associer le trajet au conducteur connecté
            Trajet savedTrajet = trajetRepository.save(trajet);

            return ResponseEntity.ok(savedTrajet);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null); // Token invalide
        }
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
    @GetMapping("/search")
    public ResponseEntity<List<Trajet>> searchTrajets(
            @RequestParam(required = false) String startLocation,
            @RequestParam(required = false) String endLocation,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Trajet> resultats = trajetRepository.search(startLocation, endLocation, date);
        return ResponseEntity.ok(resultats);
    }




    @GetMapping("")
    public ResponseEntity<List<Trajet>> searchTrajets() {
      try {
          return ResponseEntity.status(HttpStatus.OK).body(trajetRepository.findAll());

      }
      catch (Exception e) { }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
