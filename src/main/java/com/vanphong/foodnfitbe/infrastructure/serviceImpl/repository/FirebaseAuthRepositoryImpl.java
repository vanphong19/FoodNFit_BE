package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.vanphong.foodnfitbe.domain.repository.FirebaseAuthRepository;
import org.springframework.stereotype.Service;

@Service
public class FirebaseAuthRepositoryImpl implements FirebaseAuthRepository {
    @Override
    public String createUser(String email, String password) {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password);
        try {
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            return userRecord.getUid();
        } catch (Exception e) {
            throw new RuntimeException("Firebase create user failed", e);
        }
    }
}
