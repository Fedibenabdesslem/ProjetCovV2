package com.covoiturage.covoiturage2.service;

import com.covoiturage.covoiturage2.entity.Reclamation;
import com.covoiturage.covoiturage2.entity.ReclamationStatus;
import com.covoiturage.covoiturage2.repository.ReclamationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    public Reclamation save(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }

    public List<Reclamation> findAll() {
        return reclamationRepository.findAll();
    }

    public Reclamation updateStatus(Long id, String status) {
        Reclamation reclamation = reclamationRepository.findById(id).orElseThrow();
        reclamation.setStatus(Enum.valueOf(ReclamationStatus.class, status));
        return reclamationRepository.save(reclamation);
    }
}
