package com.covoiturage.covoiturage2.dto;

public class ReservationRequest {
    private Long trajetId;
    private Long passengerId;

    // Getters et Setters
    public Long getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(Long trajetId) {
        this.trajetId = trajetId;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }
}
