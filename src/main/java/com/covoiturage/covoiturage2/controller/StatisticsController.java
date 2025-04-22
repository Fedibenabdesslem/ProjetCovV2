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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrajetRepository trajetRepository;
    //@Autowired
    //private PaymentRepository paymentRepository;
    @Autowired
    private ReclamationRepository reclamationRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CONDUCTEUR')")
    public ResponseEntity<Map<String, Object>> getStatistics(@RequestParam(required = false) Long conducteurId) {
        Map<String, Object> stats = new HashMap<>();

        // Vérifier si l'utilisateur est un admin
        if (hasRoleAdmin()) {
            // Statistiques pour l'admin
            long totalUsers = userRepository.count();
            long totalTrajets = trajetRepository.count();
           // double totalPayments = paymentRepository.sumTotalPayments();
            long totalReclamations = reclamationRepository.count();

            stats.put("totalUsers", totalUsers);
            stats.put("totalTrajets", totalTrajets);
            //stats.put("totalPayments", totalPayments);
            stats.put("totalReclamations", totalReclamations);
        }

        // Vérifier si l'utilisateur est un conducteur
        if (hasRoleConducteur(conducteurId)) {
            // Statistiques pour le conducteur
            List<Trajet> topTrajets = trajetRepository.findTopTrajetsByConducteur(conducteurId);
            //double totalRevenus = paymentRepository.calculateTotalRevenueByConducteur(conducteurId);
            List<Trajet> trajetsPasses = trajetRepository.findPastTrajetsByConducteur(conducteurId);

            stats.put("topTrajets", topTrajets);
           // stats.put("totalRevenus", totalRevenus);
            stats.put("trajetsPasses", trajetsPasses);
        }

        return ResponseEntity.ok(stats);
    }

    // Vérifier si l'utilisateur a le rôle "ADMIN"
    private boolean hasRoleAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

    // Vérifier si l'utilisateur a le rôle "CONDUCTEUR" et que le conducteurId est valide
    private boolean hasRoleConducteur(Long conducteurId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CONDUCTEUR"))
                && conducteurId != null;
    }
}
