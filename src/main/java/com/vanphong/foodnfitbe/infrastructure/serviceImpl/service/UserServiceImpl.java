package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.UserService;
import com.vanphong.foodnfitbe.domain.entity.UserHistory;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.UserHistoryRepository;
import com.vanphong.foodnfitbe.domain.repository.UserRepository;
import com.vanphong.foodnfitbe.domain.specification.UserSpecification;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.UserMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserSearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserHistoryRepository userHistoryRepository;
    private final CurrentUser currentUser;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, UserHistoryRepository userHistoryRepository, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userHistoryRepository = userHistoryRepository;
        this.currentUser = currentUser;
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
                .changedBy(getFullname())
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
                .changedBy(getFullname())
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
    public Page<UserResponse> getAllUsers(UserSearchCriteria criteria) {
        int page = criteria.getPage() != null && criteria.getPage() > 0 ? criteria.getPage() - 1: 0;
        int size = criteria.getSize() != null && criteria.getSize() > 0 ? criteria.getSize() : 10;

        String sortBy = criteria.getSortBy() != null ? criteria.getSortBy() : "id";
        Sort.Direction direction = "desc".equalsIgnoreCase(criteria.getSortDir()) ? Sort.Direction.DESC : Sort.Direction.ASC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Specification<Users> spec = UserSpecification.getUsersByCriteria(criteria);
        return userRepository.findAllUsers(spec, pageRequest).map(userMapper::toResponse);
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
                .changedBy(getFullname())
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
                .changedBy(getFullname())
                .build());

        boolean curStatus = user.isBlocked();
        boolean newStatus = !curStatus;
        user.setBlocked(newStatus);
        return userMapper.toResponse(userRepository.saveUser(user));
    }

    @Override
    public Long countUsersCreateAtLastMonth() {
        YearMonth thisMonth = YearMonth.now();
        YearMonth lastMonth = thisMonth.minusMonths(1);

        LocalDate startOfLastMonth = lastMonth.atDay(1);
        LocalDate endOfLastMonth = lastMonth.atEndOfMonth();

        return userRepository.countUsersByMonth(startOfLastMonth, endOfLastMonth);
    }

    @Override
    public Long countUsers() {
        return userRepository.countUsers();
    }

    @Override
    public Long countUsersCreateAtThisMonth() {
        YearMonth thisMonth = YearMonth.now();

        LocalDate startOfMonth = thisMonth.atDay(1);
        LocalDate endOfMonth = thisMonth.atEndOfMonth();

        return userRepository.countUsersByMonth(startOfMonth, endOfMonth);
    }

    public String getFullname() {
        Optional<Users> updater =userRepository.findUser(currentUser.getCurrentUserId());
        if(updater.isEmpty()){
            throw new NotFoundException("không tìm thấy user");
        }
        return updater.get().getFullname();
    }
}
