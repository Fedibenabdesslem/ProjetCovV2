package com.covoiturage.covoiturage2.service;

import com.covoiturage.covoiturage2.entity.Trajet;
import com.covoiturage.covoiturage2.repository.TrajetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrajetService {

    @Autowired
    private TrajetRepository trajetRepository;

    public Trajet createTrajet(Trajet trajet) {
        return trajetRepository.save(trajet);
    }

    public Optional<Trajet> getTrajetById(Long id) {
        return trajetRepository.findById(id);
    }

    public List<Trajet> getTrajetsByConducteur(Long userId) {
        return trajetRepository.findByUserId(userId);
    }

    public Optional<Trajet> updateTrajet(Long id, Trajet trajetDetails) {
        return trajetRepository.findById(id).map(trajet -> {
            trajet.setStartLocation(trajetDetails.getStartLocation());
            trajet.setEndLocation(trajetDetails.getEndLocation());
            trajet.setDepartureTime(trajetDetails.getDepartureTime());
            trajet.setAvailableSeats(trajetDetails.getAvailableSeats());
            trajet.setAllowsLuggage(trajetDetails.isAllowsLuggage());
            trajet.setAllowsMusic(trajetDetails.isAllowsMusic());
            return trajetRepository.save(trajet);
        });
    }

    public boolean deleteTrajet(Long id) {
        Optional<Trajet> trajet = trajetRepository.findById(id);
        if (trajet.isPresent()) {
            trajetRepository.delete(trajet.get());
            return true;
        }
        return false;
    }
}
