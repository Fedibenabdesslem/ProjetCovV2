package com.covoiturage.covoiturage2.repository;

import com.covoiturage.covoiturage2.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    // Trouver les réservations d'un passager
    List<Reservation> findByPassengerId(Long passengerId);

    // Trouver les réservations pour un trajet
    List<Reservation> findByTrajetId(Long trajetId);
}
