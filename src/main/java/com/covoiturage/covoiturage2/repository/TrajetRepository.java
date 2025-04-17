package com.covoiturage.covoiturage2.repository;

import com.covoiturage.covoiturage2.entity.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    List<Trajet> findByUserId(Long userId); // Trouver les trajets d'un conducteur spécifique
    @Query("SELECT t FROM Trajet t WHERE " +
            "(:startLocation IS NULL OR LOWER(t.startLocation) = LOWER(:startLocation)) AND " +
            "(:endLocation IS NULL OR LOWER(t.endLocation) = LOWER(:endLocation)) AND " +
            "(:date IS NULL OR FUNCTION('DATE', t.departureTime) = :date)")
    List<Trajet> search(
            @Param("startLocation") String startLocation,
            @Param("endLocation") String endLocation,
            @Param("date") LocalDate date
    );
}
