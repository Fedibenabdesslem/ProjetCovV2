package com.covoiturage.covoiturage2.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = "http://localhost:4200")
public class StatisticsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrajetRepository trajetRepository;
    @Autowired
    private ReclamationRepository reclamationRepository;
    // Décommenter l'injection si tu as un PaymentRepository
    // @Autowired
    // private PaymentRepository paymentRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CONDUCTEUR')")
    public ResponseEntity<Map<String, Object>> getStatistics(@RequestParam(required = false) Long conducteurId) {
        Map<String, Object> stats = new HashMap<>();

        // Vérifier si l'utilisateur est un admin
        if (hasRoleAdmin()) {
            // Statistiques pour l'admin
            long totalUsers = userRepository.count();
            long totalTrajets = trajetRepository.count();
            // double totalPayments = paymentRepository.sumTotalPayments(); // Décommenter si tu as PaymentRepository
            long totalReclamations = reclamationRepository.count();

            stats.put("totalUsers", totalUsers);
            stats.put("totalTrajets", totalTrajets);
            // stats.put("totalPayments", totalPayments); // Décommenter si tu as PaymentRepository
            stats.put("totalReclamations", totalReclamations);
        }

        // Vérifier si l'utilisateur est un conducteur
        if (hasRoleConducteur(conducteurId)) {
            // Statistiques pour le conducteur
            List<Trajet> topTrajets = trajetRepository.findTopTrajetsByConducteur(conducteurId);
            // double totalRevenus = paymentRepository.calculateTotalRevenueByConducteur(conducteurId); // Décommenter si tu as PaymentRepository
            List<Trajet> trajetsPasses = trajetRepository.findPastTrajetsByConducteur(conducteurId);

            stats.put("topTrajets", topTrajets);
            // stats.put("totalRevenus", totalRevenus); // Décommenter si tu as PaymentRepository
            stats.put("trajetsPasses", trajetsPasses);
        }

        return ResponseEntity.ok(stats);
    }

    // Vérifier si l'utilisateur a le rôle "ADMIN"
    private boolean hasRoleAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    // Vérifier si l'utilisateur a le rôle "CONDUCTEUR" et que le conducteurId est valide
    private boolean hasRoleConducteur(Long conducteurId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CONDUCTEUR"))
                && conducteurId != null;
    }
}
