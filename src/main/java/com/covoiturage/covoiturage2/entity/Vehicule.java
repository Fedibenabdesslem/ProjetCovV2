package com.covoiturage.covoiturage2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // Relation ManyToOne avec l'entité User
    @JoinColumn(name = "user_id", nullable = false)  // Nom de la colonne pour la clé étrangère
    private User user;

    private String vehiculeModel;
    private String vehiculeImmatriculation;
    private String vehiculeColor;
    private int seatingCapacity;
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructeurs, getters, et setters
    public Vehicule() {}

    public Vehicule(Long id, User user, String vehiculeModel, String vehiculeImmatriculation, String vehiculeColor,
                    int seatingCapacity, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.vehiculeModel = vehiculeModel;
        this.vehiculeImmatriculation = vehiculeImmatriculation;
        this.vehiculeColor = vehiculeColor;
        this.seatingCapacity = seatingCapacity;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getVehiculeModel() {
        return vehiculeModel;
    }

    public String getVehiculeImmatriculation() {
        return vehiculeImmatriculation;
    }

    public String getVehiculeColor() {
        return vehiculeColor;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
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

    public void setVehiculeModel(String vehiculeModel) {
        this.vehiculeModel = vehiculeModel;
    }

    public void setVehiculeImmatriculation(String vehiculeImmatriculation) {
        this.vehiculeImmatriculation = vehiculeImmatriculation;
    }

    public void setVehiculeColor(String vehiculeColor) {
        this.vehiculeColor = vehiculeColor;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
