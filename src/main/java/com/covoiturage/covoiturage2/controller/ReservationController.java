package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.dto.ReservationRequest;
import com.covoiturage.covoiturage2.entity.Reservation;
import com.covoiturage.covoiturage2.entity.BookingStatus;
import com.covoiturage.covoiturage2.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // Créer une réservation
    @PostMapping
    @PreAuthorize("hasRole('PASSAGER')")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest request) {
        try {
            Reservation reservation = reservationService.createReservation(request.getTrajetId(), request.getPassengerId());
            return ResponseEntity.ok(reservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    // Récupérer toutes les réservations d’un passager
    @GetMapping("/passager/{passengerId}")
    @PreAuthorize("hasRole('ROLE_PASSAGER')")
    public ResponseEntity<List<Reservation>> getReservationsByPassenger(@PathVariable Long passengerId) {
        return ResponseEntity.ok(reservationService.getReservationsByPassenger(passengerId));
    }

    // Récupérer les réservations pour un trajet (utile pour les conducteurs)
    @GetMapping("/trajet/{trajetId}")
    public ResponseEntity<List<Reservation>> getReservationsByTrajet(@PathVariable Long trajetId) {
        return ResponseEntity.ok(reservationService.getReservationsByTrajet(trajetId));
    }

    // Mettre à jour le statut d'une réservation
    // Mettre à jour le statut d'une réservation
    @PutMapping("/{reservationId}/status")
    @PreAuthorize("hasAnyAuthority('CONDUCTEUR', 'ADMIN')")

    public ResponseEntity<Reservation> updateReservationStatus(
            @PathVariable Long reservationId,
            @RequestBody BookingStatus status) { // Accepte le statut dans le corps de la requête
        try {
            Reservation updatedReservation = reservationService.updateReservationStatus(reservationId, status);
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
