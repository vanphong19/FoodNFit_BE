package com.vanphong.foodnfitbe.presentation.mapper;

import com.vanphong.foodnfitbe.domain.entity.Exercise;
import com.vanphong.foodnfitbe.domain.entity.UserHistory;
import com.vanphong.foodnfitbe.domain.entity.Users;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.request.UserUpdateRequest;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.ExerciseResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserHistoryResponse;
import com.vanphong.foodnfitbe.presentation.viewmodel.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class UserMapper {
    public Users toEntity(UserRequest userRequest) {
        return Users.builder()
                .email(userRequest.getEmail())
                .fullname(userRequest.getFullname())
                .gender(userRequest.isGender())
                .avatarUrl(userRequest.getAvatarUrl())
                .birthday(userRequest.getBirthday())
                .active(true)
                .blocked(false)
                .build();
    }

    public UserResponse toResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullname(user.getFullname())
                .gender(user.isGender())
                .avatarUrl(user.getAvatarUrl())
                .birthday(user.getBirthday())
                .active(user.isActive())
                .blocked(user.isBlocked())
                .history(user.getHistories() != null ?
                        user.getHistories().stream()
                                .map(this::toHistoryResponse)
                                .collect(Collectors.toList()) : List.of())
                .build();
    }

    public UserHistoryResponse toHistoryResponse(UserHistory h) {
        return UserHistoryResponse.builder()
                .id(h.getId())
                .email(h.getEmail())
                .fullname(h.getFullname())
                .gender(h.isGender())
                .birthday(h.getBirthday())
                .avatarUrl(h.getAvatarUrl())
                .isActive(h.getIsActive())
                .changedAt(h.getChangedAt())
                .changeType(h.getChangeType())
                .changedBy(h.getChangedBy())
                .build();
    }

    public List<UserResponse> toResponseList(List<Users> users) {
        return users.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
