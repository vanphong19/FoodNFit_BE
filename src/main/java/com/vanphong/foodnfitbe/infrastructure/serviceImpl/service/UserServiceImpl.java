package com.vanphong.foodnfitbe.infrastructure.serviceImpl.service;

import com.vanphong.foodnfitbe.application.service.UserService;
import com.vanphong.foodnfitbe.domain.entity.UserHistory;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.domain.repository.*;
import com.vanphong.foodnfitbe.domain.specification.UserSpecification;
import com.vanphong.foodnfitbe.exception.NotFoundException;
import com.vanphong.foodnfitbe.presentation.mapper.UserMapper;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserSearchCriteria;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserUpdateRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.NutritionDto;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserDailyStatsDto;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserResponse;
import com.vanphong.foodnfitbe.utils.CurrentUser;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class    UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserHistoryRepository userHistoryRepository;
    private final CurrentUser currentUser;
    private final FoodLogRepository logRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserProfileRepository userProfileRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, UserHistoryRepository userHistoryRepository, CurrentUser currentUser, FoodLogRepository logRepository, WorkoutPlanRepository workoutPlanRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userHistoryRepository = userHistoryRepository;
        this.currentUser = currentUser;
        this.logRepository = logRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.userProfileRepository = userProfileRepository;
    }


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // Tạo và lưu user trước
        Users user = userMapper.toEntity(userRequest);
        user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));
        user.setCreatedDate(LocalDate.now());

        // Lưu user và flush để đảm bảo ID được generate
        Users savedUser = userRepository.saveAndFlush(user); // Thay saveUser bằng saveAndFlush

        System.out.println("Saved user ID: " + savedUser.getId());

        // Tạo UserHistory sau khi user đã được lưu và có ID
        UserHistory history = UserHistory.builder()
                .user(savedUser) // Sử dụng savedUser thay vì tìm lại
                .email(savedUser.getEmail())
                .passwordHash(savedUser.getPasswordHash())
                .fullname(savedUser.getFullname())
                .gender(savedUser.isGender())
                .birthday(savedUser.getBirthday())
                .avatarUrl(savedUser.getAvatarUrl())
                .isActive(savedUser.isActive())
                .changeType("CREATE")
                .changedAt(LocalDateTime.now())
                .changedBy(getFullname())
                .build();

        userHistoryRepository.save(history);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(UUID id, UserUpdateRequest userRequest) {
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
                .changedAt(LocalDateTime.now())
                .changedBy(getFullname())
                .build());

        // Cập nhật user
        user.setFullname(userRequest.getFullname());
        user.setAvatarUrl(userRequest.getAvatarUrl());
        user.setGender(userRequest.isGender());
        user.setUpdatedDate(LocalDate.now());
        user.setBirthday(userRequest.getBirthday());
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
                .changedAt(LocalDateTime.now())
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
                .changedAt(LocalDateTime.now())
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

    @Override
    public UserDailyStatsDto getUserDailyStats() {
        UUID userId = currentUser.getCurrentUserId();
        LocalDate today = LocalDate.now();
        NutritionDto nutrition = logRepository.getNutritionStats(userId, today);
        Double caloriesOut = workoutPlanRepository.getCaloriesOut(userId, today);
        Double TDEE = userProfileRepository.getLatestTDEE(userId);

        return new UserDailyStatsDto(
                nutrition.calories().doubleValue(),
                nutrition.protein().doubleValue(),
                nutrition.carbs().doubleValue(),
                nutrition.fat().doubleValue(),
                caloriesOut,
                TDEE
        );
    }

    public String getFullname() {
        Optional<Users> updater =userRepository.findUser(currentUser.getCurrentUserId());
        if(updater.isEmpty()){
            throw new NotFoundException("không tìm thấy user");
        }
        return updater.get().getFullname();
    }
}
