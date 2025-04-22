package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.entity.Evaluation;
import com.covoiturage.covoiturage2.service.EvaluationService;
import com.covoiturage.covoiturage2.dto.EvaluationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    // Permettre à un passager d'évaluer un conducteur
    @PostMapping("/submit")
    @PreAuthorize("hasAuthority('ROLE_PASSAGER')")
    public Evaluation submitEvaluation(@RequestBody EvaluationDto evaluationDto) {
        return evaluationService.submitEvaluation(evaluationDto);
    }

    // Récupérer les évaluations d'un conducteur
    @GetMapping("/conducteur/{conducteurId}")
    @PreAuthorize("hasAnyAuthority('ROLE_CONDUCTEUR', 'ROLE_PASSAGER')")
    public List<Evaluation> getEvaluationsByConducteur(@PathVariable Long conducteurId) {
        return evaluationService.getEvaluationsByConducteur(conducteurId);
    }
}
