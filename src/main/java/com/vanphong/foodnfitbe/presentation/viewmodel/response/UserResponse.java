package com.vanphong.foodnfitbe.presentation.viewmodel.response;

import com.vanphong.foodnfitbe.domain.entity.UserHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String email;
    private String fullname;
    private boolean gender;
    private LocalDate birthday;
    private String avatarUrl;
    private boolean active;
    private boolean blocked;

    private List<UserHistoryResponse> history;
}
