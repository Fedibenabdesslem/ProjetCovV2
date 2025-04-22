package com.covoiturage.covoiturage2.dto;

public class EvaluationDto {

    private Long passagerId;   // ID du passager qui évalue
    private Long conducteurId; // ID du conducteur à évaluer
    private Long trajetId;     // ID du trajet associé à l'évaluation
    private int note;          // Note de l'évaluation (par exemple, sur une échelle de 1 à 5)
    private String commentaire; // Commentaire laissé par le passager

    // Getters et Setters

    public Long getPassagerId() {
        return passagerId;
    }

    public void setPassagerId(Long passagerId) {
        this.passagerId = passagerId;
    }

    public Long getConducteurId() {
        return conducteurId;
    }

    public void setConducteurId(Long conducteurId) {
        this.conducteurId = conducteurId;
    }

    public Long getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(Long trajetId) {
        this.trajetId = trajetId;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
