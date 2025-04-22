package com.covoiturage.covoiturage2.repository;

import com.covoiturage.covoiturage2.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByConducteurId(Long conducteurId);
}

