package com.vanphong.foodnfitbe.domain.repository;

import com.vanphong.foodnfitbe.domain.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {
    Optional<Users> findByEmail(String email);
    Users saveUser(Users user);
    Optional<Users> findUser(UUID id);
    Page<Users> findAllUsers(Specification<Users> specification, PageRequest pageRequest);
    void deleteUser(UUID id);
    Long countUsersByMonth(LocalDate from, LocalDate to);
    Long countUsers();
    Users saveAndFlush(Users user);
    List<Users> findAllUsers();
}
