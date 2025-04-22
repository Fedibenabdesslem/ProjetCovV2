package com.covoiturage.covoiturage2.service;

import com.covoiturage.covoiturage2.entity.Message;
import com.covoiturage.covoiturage2.entity.User;
import com.covoiturage.covoiturage2.repository.MessageRepository;
import com.covoiturage.covoiturage2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Message sendMessage(Message message) {
        User sender = userRepository.findById(message.getSender().getId())
                .orElseThrow(() -> new RuntimeException("Expéditeur introuvable"));

        User receiver = userRepository.findById(message.getReceiver().getId())
                .orElseThrow(() -> new RuntimeException("Destinataire introuvable"));

        message.setSender(sender);
        message.setReceiver(receiver);

        return messageRepository.save(message);
    }

    public List<Message> getChat(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messagePage = messageRepository.findBySenderIdOrReceiverId(userId, userId, pageable);
        return messagePage.getContent();
    }
}
