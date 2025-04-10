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

    private String reclamationType;
    private String description;
    private String status;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructeurs, getters, et setters
    public Reclamation() {}

    public Reclamation(Long id, User user, String reclamationType, String description, String status, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.reclamationType = reclamationType;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getReclamationType() {
        return reclamationType;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
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

    public void setReclamationType(String reclamationType) {
        this.reclamationType = reclamationType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
