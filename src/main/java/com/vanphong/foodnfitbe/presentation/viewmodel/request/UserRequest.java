package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserRequest {
    private String email;
    private String password;
    private String fullname;
    private boolean gender;
    private LocalDate birthday;
    private String avatarUrl;
}
