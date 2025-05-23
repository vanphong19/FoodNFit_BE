package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    private String email;
    private String password;
    private String fullname;
    private boolean gender;
    private LocalDate birthday;
    private String avatarUrl;
}
