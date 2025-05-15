package com.covoiturage.covoiturage2.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ConducteurStatisticsDTO {

    private List<SimpleTrajetDTO> topTrajets;
    private List<SimpleTrajetDTO> trajetsPasses;

    public ConducteurStatisticsDTO(List<SimpleTrajetDTO> topTrajets, List<SimpleTrajetDTO> trajetsPasses) {
        this.topTrajets = topTrajets;
        this.trajetsPasses = trajetsPasses;
    }

    public List<SimpleTrajetDTO> getTopTrajets() {
        return topTrajets;
    }

    public void setTopTrajets(List<SimpleTrajetDTO> topTrajets) {
        this.topTrajets = topTrajets;
    }

    public List<SimpleTrajetDTO> getTrajetsPasses() {
        return trajetsPasses;
    }

    public void setTrajetsPasses(List<SimpleTrajetDTO> trajetsPasses) {
        this.trajetsPasses = trajetsPasses;
    }
}
