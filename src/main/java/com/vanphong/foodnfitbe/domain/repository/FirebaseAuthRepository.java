package com.vanphong.foodnfitbe.domain.repository;

public interface FirebaseAuthRepository {
    String createUser(String email, String password);
}
