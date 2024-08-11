package com.example.service;

import com.example.dto.extra.Registration;
import com.example.entity.EmailSentHistoryEntity;
import com.example.repository.EmailSentHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailHistoryService {
    @Autowired
    private EmailSentHistoryRepository emailSentHistoryRepository;

    /**
     * this method is needed to write a link to the history when an sms is sent to an email 👇🏻
     */

    public void create(Registration dto, String text) {
        EmailSentHistoryEntity entity = new EmailSentHistoryEntity();
        entity.setEmail(dto.getEmail());
        entity.setMessage(text);
        emailSentHistoryRepository.save(entity);
    }

}
