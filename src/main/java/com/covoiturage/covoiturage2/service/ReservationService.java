package com.covoiturage.covoiturage2.service;

import com.covoiturage.covoiturage2.entity.Reservation;
import com.covoiturage.covoiturage2.entity.Trajet;
import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.entity.BookingStatus;
import com.covoiturage.covoiturage2.repository.ReservationRepository;
import com.covoiturage.covoiturage2.repository.TrajetRepository;
import com.covoiturage.covoiturage2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private UserRepository userRepository;

    // Créer une réservation
    public Reservation createReservation(Long trajetId, Long passengerId) {
        Optional<Trajet> trajetOpt = trajetRepository.findById(trajetId);
        Optional<User> passengerOpt = userRepository.findById(passengerId);

        if (trajetOpt.isEmpty() || passengerOpt.isEmpty()) {
            throw new RuntimeException("Trajet ou passager introuvable.");
        }

        Trajet trajet = trajetOpt.get();
        User passenger = passengerOpt.get();

        // Vérifier qu'il y a des places disponibles
        if (trajet.getAvailableSeats() <= 0) {
            throw new RuntimeException("Aucune place disponible pour ce trajet.");
        }

        // Créer la réservation
        Reservation reservation = new Reservation(passenger, trajet, BookingStatus.EN_ATTENTE);
        reservation.setCreatedAt(LocalDateTime.now());

        // Diminuer le nombre de places disponibles
        trajet.setAvailableSeats(trajet.getAvailableSeats() - 1);
        trajetRepository.save(trajet);

        return reservationRepository.save(reservation);
    }

    // Mettre à jour le statut de la réservation
    public Reservation updateReservationStatus(Long reservationId, BookingStatus status) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

        if (optionalReservation.isEmpty()) {
            throw new RuntimeException("Réservation introuvable.");
        }

        Reservation reservation = optionalReservation.get();
        reservation.setBookingStatus(status);
        return reservationRepository.save(reservation);
    }

    // Récupérer les réservations d'un passager
    public List<Reservation> getReservationsByPassenger(Long passengerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();  // Obtenir l'email de l'utilisateur connecté

        Optional<User> currentUser = userRepository.findByEmail(username);  // Assure-toi que ton UserRepository a cette méthode

        if (currentUser.isPresent() && currentUser.get().getId().equals(passengerId)) {
            return reservationRepository.findByPassengerId(passengerId);
        } else {
            throw new RuntimeException("Accès non autorisé.");
        }
    }

    // Récupérer les réservations d'un trajet
    public List<Reservation> getReservationsByTrajet(Long trajetId) {
        return reservationRepository.findByTrajetId(trajetId);
    }
}
