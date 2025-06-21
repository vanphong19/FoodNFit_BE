package com.vanphong.foodnfitbe.infrastructure.serviceImpl.repository;

import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public Users saveUser(Users user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<Users> findUser(UUID id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Page<Users> findAllUsers(Specification<Users> specification, PageRequest pageRequest) {
        return userJpaRepository.findAll(specification, pageRequest);
    }

    @Override
    public void deleteUser(UUID id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public Long countUsersByMonth(LocalDate from, LocalDate to) {
        return userJpaRepository.countByCreatedDateBetweenAndActiveTrue(from, to);
    }

    @Override
    public Long countUsers() {
        return userJpaRepository.count();
    }

    @Override
    public Users saveAndFlush(Users user) {
        return userJpaRepository.saveAndFlush(user);
    }

    @Override
    public List<Users> findAllUsers() {
        return userJpaRepository.findAllByActiveTrue();
    }
}
