package com.covoiturage.covoiturage2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Abonnement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Relation ManyToOne avec l'entité User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String subscriptionType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt = LocalDateTime.now();
    private double montant;

    // Constructeurs, getters, et setters
    public Abonnement() {}

    public Abonnement(Long id, User user, String subscriptionType, LocalDateTime startDate, LocalDateTime endDate,
                      LocalDateTime createdAt, double montant) {
        this.id = id;
        this.user = user;
        this.subscriptionType = subscriptionType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.montant = montant;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public double getMontant() {
        return montant;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}
