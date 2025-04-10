package com.covoiturage.covoiturage2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relation ManyToOne avec l'entité User (passager)
    @JoinColumn(name = "passenger_id", nullable = false)
    private User passenger; // Passager qui effectue la réservation

    @ManyToOne(fetch = FetchType.LAZY) // Relation ManyToOne avec l'entité Trajet
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet; // Trajet réservé

    private String bookingStatus; // Statut de la réservation (ex: confirmée, en attente)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Constructeurs, getters et setters
    public Reservation() {}

    public Reservation(Long id, User passenger, Trajet trajet, String bookingStatus, LocalDateTime createdAt) {
        this.id = id;
        this.passenger = passenger;
        this.trajet = trajet;
        this.bookingStatus = bookingStatus;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public User getPassenger() {
        return passenger;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
