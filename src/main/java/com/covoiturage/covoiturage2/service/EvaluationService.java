package com.covoiturage.covoiturage2.service;

import com.covoiturage.covoiturage2.dto.EvaluationDto;
import com.covoiturage.covoiturage2.entity.Evaluation;
import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.entity.Trajet;
import com.covoiturage.covoiturage2.repository.EvaluationRepository;
import com.covoiturage.covoiturage2.repository.UserRepository;
import com.covoiturage.covoiturage2.repository.TrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    // Soumettre une évaluation d'un passager pour un conducteur
    public Evaluation submitEvaluation(EvaluationDto evaluationDto) {
        User passager = userRepository.findById(evaluationDto.getPassagerId())
                .orElseThrow(() -> new RuntimeException("Passager non trouvé"));

        User conducteur = userRepository.findById(evaluationDto.getConducteurId())
                .orElseThrow(() -> new RuntimeException("Conducteur non trouvé"));

        Trajet trajet = trajetRepository.findById(evaluationDto.getTrajetId())
                .orElseThrow(() -> new RuntimeException("Trajet non trouvé"));

        Evaluation evaluation = new Evaluation();
        evaluation.setPassager(passager);
        evaluation.setConducteur(conducteur);
        evaluation.setTrajet(trajet);
        evaluation.setNote(evaluationDto.getNote());
        evaluation.setCommentaire(evaluationDto.getCommentaire());
        evaluation.setDateEvaluation(LocalDateTime.now());

        return evaluationRepository.save(evaluation);
    }

    // Récupérer toutes les évaluations d'un conducteur
    public List<Evaluation> getEvaluationsByConducteur(Long conducteurId) {
        return evaluationRepository.findByConducteurId(conducteurId);
    }
}
