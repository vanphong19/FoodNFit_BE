package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.vanphong.foodnfitbe.application.service.FcmService;
import com.vanphong.foodnfitbe.domain.entity.FcmToken;
import com.vanphong.foodnfitbe.domain.entity.Reminders;
import com.vanphong.foodnfitbe.domain.repository.FcmTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {
    private final FcmTokenRepository fcmTokenRepository;
    @Override
    public void send(Reminders reminder) {
        List<FcmToken> tokens = fcmTokenRepository.findByUser(reminder.getUser());
        for (FcmToken token : tokens) {
            Message message = Message.builder()
                    .setToken(token.getToken())
                    .putData("title", reminder.getReminderType())
                    .putData("message", reminder.getMessage())
                    .putData("schedule_time", reminder.getScheduledTime().toString())
                    .build();
            try{
                String response = FirebaseMessaging.getInstance().send(message);
                System.out.println("sent: " + response);
            }
            catch (FirebaseMessagingException e){
                System.err.println("Send error" + e.getMessage());
            }

        }
    }
}
