package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.UserService;
import com.vanphong.foodnfitbe.domain.entity.UserHistory;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.UserHistoryRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.infrastructure.jpaRepository.UserHistoryJpaRepository;
import com.vanphong.foodnfitbe.presentation.mapper.UserMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserHistoryRepository userHistoryRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, UserHistoryRepository userHistoryRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userHistoryRepository = userHistoryRepository;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        Users user = userMapper.toEntity(userRequest);
        user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedDate(LocalDate.now());
        Users saved = userRepository.saveUser(user);
        userHistoryRepository.save(UserHistory.builder()
                .user(saved)
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .fullname(user.getFullname())
                .gender(user.isGender())
                .birthday(user.getBirthday())
                .avatarUrl(user.getAvatarUrl())
                .isActive(user.isActive())
                .changeType("CREATE")
                .changedAt(LocalTime.now())
                .build());
        return userMapper.toResponse(saved);
    }

    @Override
    public UserResponse updateUser(UUID id, UserRequest userRequest) {
        Users user = userRepository.findUser(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
        // Log lịch sử trước khi thay đổi
        userHistoryRepository.save(UserHistory.builder()
                .user(user)
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .fullname(user.getFullname())
                .gender(user.isGender())
                .birthday(user.getBirthday())
                .avatarUrl(user.getAvatarUrl())
                .isActive(user.isActive())
                .changeType("UPDATE")
                .changedAt(LocalTime.now())
                .build());

        // Cập nhật user
        user.setFullname(userRequest.getFullname());
        user.setAvatarUrl(userRequest.getAvatarUrl());
        user.setGender(userRequest.isGender());
        user.setUpdatedDate(LocalDate.now());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
        }
        return userMapper.toResponse(userRepository.saveUser(user));
    }

    @Override
    public UserResponse getById(UUID id) {
        Users user = userRepository.findUser(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));        UserResponse response = userMapper.toResponse(user);
        response.setHistory(user.getHistories().stream().map(userMapper::toHistoryResponse).collect(Collectors.toList()));
        return response;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<Users> users = userRepository.findAllUsers();
        return userMapper.toResponseList(users);
    }

    @Override
    public UserResponse deleteUser(UUID id) {
        Users user = userRepository.findUser(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));        UserResponse response = userMapper.toResponse(user);

        userHistoryRepository.save(UserHistory.builder()
                .user(user)
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .fullname(user.getFullname())
                .gender(user.isGender())
                .birthday(user.getBirthday())
                .avatarUrl(user.getAvatarUrl())
                .isActive(user.isActive())
                .changeType("DELETE")
                .changedAt(LocalTime.now())
                .build());

        user.setActive(false);
        return userMapper.toResponse(userRepository.saveUser(user));
    }

    @Override
    public UserResponse blockUser(UUID id) {
        Users user = userRepository.findUser(id).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));        UserResponse response = userMapper.toResponse(user);

        userHistoryRepository.save(UserHistory.builder()
                .user(user)
                .email(user.getEmail())
                .passwordHash(user.getPasswordHash())
                .fullname(user.getFullname())
                .gender(user.isGender())
                .birthday(user.getBirthday())
                .avatarUrl(user.getAvatarUrl())
                .isActive(user.isActive())
                .changeType("BLOCK")
                .changedAt(LocalTime.now())
                .build());

        boolean curStatus = user.isBlock();
        boolean newStatus = !curStatus;
        user.setBlock(newStatus);
        return userMapper.toResponse(userRepository.saveUser(user));
    }
}
