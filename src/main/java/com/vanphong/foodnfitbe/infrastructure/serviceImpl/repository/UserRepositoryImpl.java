package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public Users saveUser(Users user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<Users> findUser(UUID id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public List<Users> findAllUsers() {
        return jpaUserRepository.findAll();
    }

    @Override
    public void deleteUser(UUID id) {
        jpaUserRepository.deleteById(id);
    }
}
