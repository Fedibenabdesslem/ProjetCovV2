package com.covoiturage.covoiturage2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Remplace la clé secrète par une clé plus complexe et sécurisée
    private final String SECRET_KEY = "your_secret_key_here_your_secret_key_here";
    private final long EXPIRATION_TIME = 86400000; // 1 jour en millisecondes

    // Méthode pour obtenir la clé de signature à partir de la clé secrète
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Méthode pour générer un token JWT
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // L'email de l'utilisateur est le sujet du token
                .setIssuedAt(new Date()) // La date de création du token
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiration après 1 jour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Signature avec la clé secrète et l'algorithme HS256
                .compact(); // Construction du token
    }

    // Méthode pour extraire l'email du token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject); // Extraire le sujet (email) du token
    }

    // Méthode pour valider le token avec l'email de l'utilisateur
    public boolean validateToken(String token, String userEmail) {
        return (userEmail.equals(extractEmail(token)) && !isTokenExpired(token)); // Vérifie si l'email du token correspond et si le token n'est pas expiré
    }

    // Méthode pour vérifier si le token est expiré
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date()); // Comparaison de la date d'expiration du token avec la date actuelle
    }

    // Méthode générique pour extraire des informations du token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Utilisation de la clé secrète pour vérifier la signature
                .build()
                .parseClaimsJws(token) // Parsing du token
                .getBody(); // Récupération du corps des informations
        return claimsResolver.apply(claims); // Application de la fonction pour extraire l'information demandée (email, expiration...)
    }
}
