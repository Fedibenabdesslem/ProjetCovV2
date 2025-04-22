package com.covoiturage.covoiturage2.dto;

public class ReclamationRequest {
    private Long conducteurId;
    private String nomConducteur;
    private String nomPassager;
    private String numeroTelephonePassager;
    private String description;

    // Getters et Setters

    public Long getConducteurId() {
        return conducteurId;
    }

    public void setConducteurId(Long conducteurId) {
        this.conducteurId = conducteurId;
    }

    public String getNomConducteur() {
        return nomConducteur;
    }

    public void setNomConducteur(String nomConducteur) {
        this.nomConducteur = nomConducteur;
    }

    public String getNomPassager() {
        return nomPassager;
    }

    public void setNomPassager(String nomPassager) {
        this.nomPassager = nomPassager;
    }

    public String getNumeroTelephonePassager() {
        return numeroTelephonePassager;
    }

    public void setNumeroTelephonePassager(String numeroTelephonePassager) {
        this.numeroTelephonePassager = numeroTelephonePassager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

