package com.covoiturage.covoiturage2.dto;

import com.covoiturage.covoiturage2.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class TrajetUserDto {
    private Long id;
    private String startLocation;
    private String endLocation;
    private LocalDateTime departureTime;
    private int availableSeats;
    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean allowsLuggage;
    private boolean allowsMusic;
    private String firstName;
    private String lastName;
    private String phoneNumber;
     private double price;


    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAllowsLuggage() {
        return allowsLuggage;
    }

    public void setAllowsLuggage(boolean allowsLuggage) {
        this.allowsLuggage = allowsLuggage;
    }

    public boolean isAllowsMusic() {
        return allowsMusic;
    }

    public void setAllowsMusic(boolean allowsMusic) {
        this.allowsMusic = allowsMusic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}