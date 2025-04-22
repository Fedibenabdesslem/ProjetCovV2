package com.covoiturage.covoiturage2.repository;

import com.covoiturage.covoiturage2.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository pour l'entité Message
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Trouver tous les messages envoyés ou reçus par un utilisateur
    // List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    // Optionnellement, tu peux ajouter une méthode pour trouver les messages d'un trajet spécifique
    List<Message> findByTrajetId(Long trajetId);

    // Méthode pour trouver tous les messages envoyés ou reçus par un utilisateur avec pagination
    Page<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId, Pageable pageable);
}
