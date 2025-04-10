package com.covoiturage.covoiturage2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relation ManyToOne avec l'entité Reservation
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation; // Réservation liée au paiement

    private String paymentMethod; // Méthode de paiement (ex: carte, PayPal)
    private double amount; // Montant payé
    private LocalDateTime paymentDate = LocalDateTime.now();
    private String paymentStatus; // Statut du paiement (ex: payé, échoué)

    // Constructeurs, getters et setters
    public Paiement() {}

    public Paiement(Long id, Reservation reservation, String paymentMethod, double amount, LocalDateTime paymentDate, String paymentStatus) {
        this.id = id;
        this.reservation = reservation;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
