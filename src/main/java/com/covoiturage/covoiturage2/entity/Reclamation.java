package com.covoiturage.covoiturage2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Relation ManyToOne avec l'entité User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String nomConducteur;
    private String nomPassager;
    private String numeroTelephonePassager;

    @Enumerated(EnumType.STRING)
    private ReclamationStatus status;

    private String description;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructeurs, getters, et setters
    public Reclamation() {}

    public Reclamation(Long id, User user, String nomConducteur, String nomPassager,
                       String numeroTelephonePassager, ReclamationStatus status,
                       String description, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.nomConducteur = nomConducteur;
        this.nomPassager = nomPassager;
        this.numeroTelephonePassager = numeroTelephonePassager;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getNomConducteur() {
        return nomConducteur;
    }

    public String getNomPassager() {
        return nomPassager;
    }

    public String getNumeroTelephonePassager() {
        return numeroTelephonePassager;
    }

    public ReclamationStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setNomConducteur(String nomConducteur) {
        this.nomConducteur = nomConducteur;
    }

    public void setNomPassager(String nomPassager) {
        this.nomPassager = nomPassager;
    }

    public void setNumeroTelephonePassager(String numeroTelephonePassager) {
        this.numeroTelephonePassager = numeroTelephonePassager;
    }

    public void setStatus(ReclamationStatus status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
