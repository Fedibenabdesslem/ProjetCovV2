package com.covoiturage.covoiturage2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Trajet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relation ManyToOne avec l'entité User (conducteur)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Conducteur du trajet

    private String startLocation;
    private String endLocation;
    private LocalDateTime departureTime;
    private int availableSeats;
    private LocalDateTime createdAt = LocalDateTime.now();
    private Double price;


    private boolean allowsLuggage; // Autorisation des bagages
    private boolean allowsMusic; // Autorisation de la musique

    // Constructeurs, getters et setters
    public Trajet() {}

    public Trajet(Long id, User user, String startLocation, String endLocation, LocalDateTime departureTime, int availableSeats, boolean allowsLuggage, boolean allowsMusic) {
        this.id = id;
        this.user = user;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
        this.allowsLuggage = allowsLuggage;
        this.allowsMusic = allowsMusic;
        this.price = price;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isAllowsLuggage() {
        return allowsLuggage;
    }

    public boolean isAllowsMusic() {
        return allowsMusic;
    }

    public double getPrice() { return price;}

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAllowsLuggage(boolean allowsLuggage) {
        this.allowsLuggage = allowsLuggage;
    }

    public void setAllowsMusic(boolean allowsMusic) {
        this.allowsMusic = allowsMusic;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
