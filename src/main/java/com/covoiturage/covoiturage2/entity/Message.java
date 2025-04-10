package com.covoiturage.covoiturage2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Relation ManyToOne avec l'entité User (expéditeur)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender; // L'utilisateur qui envoie le message

    @ManyToOne(fetch = FetchType.LAZY) // Relation ManyToOne avec l'entité User (récepteur)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver; // L'utilisateur qui reçoit le message

    @ManyToOne(fetch = FetchType.LAZY) // Relation ManyToOne avec l'entité Trajet
    @JoinColumn(name = "trajet_id", nullable = true)
    private Trajet trajet; // Trajet associé au message (optionnel)

    private LocalDateTime createdAt = LocalDateTime.now();
    private String messageContent;

    // Constructeurs, getters et setters
    public Message() {}

    public Message(Long id, User sender, User receiver, Trajet trajet, String messageContent, LocalDateTime createdAt) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.trajet = trajet;
        this.messageContent = messageContent;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
