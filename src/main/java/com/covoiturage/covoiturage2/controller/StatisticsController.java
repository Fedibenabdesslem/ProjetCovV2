package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.dto.ConducteurStatisticsDTO;
import com.covoiturage.covoiturage2.dto.SimpleTrajetDTO;
import com.covoiturage.covoiturage2.entity.Trajet;
import com.covoiturage.covoiturage2.repository.ReclamationRepository;
import com.covoiturage.covoiturage2.repository.TrajetRepository;
import com.covoiturage.covoiturage2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrajetRepository trajetRepository;
    @Autowired
    private ReclamationRepository reclamationRepository;

    // --- Endpoint pour les statistiques ADMIN ---
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getAdminStatistics() {
        Map<String, Object> stats = Map.of(
                "totalUsers", userRepository.count(),
                "totalTrajets", trajetRepository.count(),
                "totalReclamations", reclamationRepository.count()
                // "totalPayments", paymentRepository.sumTotalPayments() si applicable
        );

        return ResponseEntity.ok(stats);
    }

    // --- Endpoint pour les statistiques CONDUCTEUR ---
    @GetMapping("/conducteur")
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<ConducteurStatisticsDTO> getConducteurStatistics(@RequestParam Long conducteurId) {

        List<Trajet> topTrajets = trajetRepository.findTopTrajetsByConducteur(conducteurId);
        List<Trajet> trajetsPasses = trajetRepository.findPastTrajetsByConducteur(conducteurId);

        List<SimpleTrajetDTO> topTrajetsDTO = topTrajets.stream()
                .map(this::convertToSimpleDTO)
                .collect(Collectors.toList());

        List<SimpleTrajetDTO> trajetsPassesDTO = trajetsPasses.stream()
                .map(this::convertToSimpleDTO)
                .collect(Collectors.toList());

        ConducteurStatisticsDTO dto = new ConducteurStatisticsDTO(topTrajetsDTO, trajetsPassesDTO);
        return ResponseEntity.ok(dto);
    }

    private SimpleTrajetDTO convertToSimpleDTO(Trajet trajet) {
        return new SimpleTrajetDTO(
                trajet.getStartLocation(),
                trajet.getEndLocation(),
                trajet.getDepartureTime(),
                trajet.getAvailableSeats()
        );
    }
}
