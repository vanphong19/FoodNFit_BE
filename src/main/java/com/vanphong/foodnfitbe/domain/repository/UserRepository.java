package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.Users;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {
    Optional<Users> findByEmail(String email);
    Users saveUser(Users user);
    Optional<Users> findUser(UUID id);
    List<Users> findAllUsers();
    void deleteUser(UUID id);
}
