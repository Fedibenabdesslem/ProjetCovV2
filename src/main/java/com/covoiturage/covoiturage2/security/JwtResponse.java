package com.covoiturage.covoiturage2.security;

public class JwtResponse {
    private String token;

    // Constructeur
    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter
    public void setToken(String token) {
        this.token = token;
    }
}
