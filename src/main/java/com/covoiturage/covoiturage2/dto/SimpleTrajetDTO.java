package com.covoiturage.covoiturage2.dto;

import java.time.LocalDateTime;

public class SimpleTrajetDTO {
    private String startLocation;
    private String endLocation;
    private LocalDateTime departureTime;
    private int availableSeats;

    public SimpleTrajetDTO(String startLocation, String endLocation, LocalDateTime departureTime, int availableSeats) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.departureTime = departureTime;
        this.availableSeats = availableSeats;
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
}
