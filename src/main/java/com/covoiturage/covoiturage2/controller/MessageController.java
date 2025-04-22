package com.covoiturage.covoiturage2.controller;

import com.covoiturage.covoiturage2.dto.MessageDto;
import com.covoiturage.covoiturage2.entity.Message;
import com.covoiturage.covoiturage2.entity.Trajet;
import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.repository.MessageRepository;
import com.covoiturage.covoiturage2.repository.TrajetRepository;
import com.covoiturage.covoiturage2.repository.UserRepository;
import com.covoiturage.covoiturage2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrajetRepository trajetRepository;

    @Autowired
    private MessageRepository messageRepository;

    // Envoyer un message
    @PostMapping("/send")
    @PreAuthorize("hasAnyAuthority('ROLE_CONDUCTEUR', 'ROLE_PASSAGER')")
    public Message sendMessage(@RequestBody MessageDto dto) {
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(dto.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        Trajet trajet = trajetRepository.findById(dto.getTrajetId())
                .orElseThrow(() -> new RuntimeException("Trajet not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setTrajet(trajet);
        message.setMessageContent(dto.getMessageContent());
        message.setCreatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    // Récupérer tous les messages d'une conversation avec pagination
    @GetMapping("/chat/{userId}")
    @PreAuthorize("hasAnyAuthority('ROLE_CONDUCTEUR', 'ROLE_PASSAGER')")
    public List<Message> getChat(@PathVariable Long userId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return messageService.getChat(userId, page, size);
    }
}
