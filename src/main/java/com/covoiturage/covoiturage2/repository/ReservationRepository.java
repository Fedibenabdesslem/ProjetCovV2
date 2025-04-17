package com.covoiturage.covoiturage2.repository;

import com.covoiturage.covoiturage2.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByPassengerId(Long passengerId);
    List<Reservation> findByTrajetId(Long trajetId);
}
