package com.vanphong.foodnfitbe.presentation.viewmodel.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
