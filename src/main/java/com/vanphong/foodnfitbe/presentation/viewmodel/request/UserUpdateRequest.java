package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserUpdateRequest {
    private String fullname;
    private boolean gender;
    private LocalDate birthday;
    private String avatarUrl;
}
