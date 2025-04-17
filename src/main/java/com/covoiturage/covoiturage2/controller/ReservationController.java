package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.entity.Reservation;
import com.covoiturage.covoiturage2.entity.Trajet;
import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.entity.BookingStatus;
import com.covoiturage.covoiturage2.repository.ReservationRepository;
import com.covoiturage.covoiturage2.repository.TrajetRepository;
import com.covoiturage.covoiturage2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private UserRepository userRepository;

    // 📌 Créer une réservation
    @PostMapping
    @PreAuthorize("hasRole('PASSAGER')")
    public ResponseEntity<?> createReservation(@RequestParam Long trajetId, @RequestParam Long passengerId) {
        Optional<Trajet> trajetOpt = trajetRepository.findById(trajetId);
        Optional<User> passengerOpt = userRepository.findById(passengerId);

        if (trajetOpt.isEmpty() || passengerOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Trajet ou passager introuvable.");
        }

        Trajet trajet = trajetOpt.get();
        User passenger = passengerOpt.get();

        if (trajet.getAvailableSeats() <= 0) {
            return ResponseEntity.badRequest().body("Aucune place disponible pour ce trajet.");
        }

        Reservation reservation = new Reservation();
        reservation.setTrajet(trajet);
        reservation.setPassenger(passenger);
        reservation.setBookingStatus(BookingStatus.EN_ATTENTE); // ✅ Utilisation de l'enum
        reservation.setCreatedAt(LocalDateTime.now());

        // 🔄 Diminuer le nombre de places disponibles
        trajet.setAvailableSeats(trajet.getAvailableSeats() - 1);
        trajetRepository.save(trajet);

        return ResponseEntity.ok(reservationRepository.save(reservation));
    }

    // 📌 Récupérer toutes les réservations d’un passager
    @GetMapping("/passager/{passengerId}")
    public ResponseEntity<List<Reservation>> getReservationsByPassenger(@PathVariable Long passengerId) {
        return ResponseEntity.ok(reservationRepository.findByPassengerId(passengerId));
    }

    // 📌 Récupérer les réservations pour un trajet (utile pour les conducteurs)
    @GetMapping("/trajet/{trajetId}")
    public ResponseEntity<List<Reservation>> getReservationsByTrajet(@PathVariable Long trajetId) {
        return ResponseEntity.ok(reservationRepository.findByTrajetId(trajetId));
    }
    //////////////////////////////////////////////////
    // ✅ Seul le conducteur peut changer le statut d'une réservation
    @PutMapping("/{reservationId}/status")
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<?> updateReservationStatus(
            @PathVariable Long reservationId,
            @RequestParam BookingStatus status,
            @RequestParam Long conducteurId
    ) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isEmpty()) {
            return ResponseEntity.badRequest().body("Réservation introuvable.");
        }

        Reservation reservation = optionalReservation.get();
        Trajet trajet = reservation.getTrajet();

        // Vérifie que le conducteur est bien le propriétaire du trajet
        if (!trajet.getUser().getId().equals(conducteurId)) {
            return ResponseEntity.status(403).body("Vous n'êtes pas autorisé à modifier cette réservation.");
        }

        reservation.setBookingStatus(status);
        reservationRepository.save(reservation);

        return ResponseEntity.ok("Statut mis à jour avec succès.");
    }

}
