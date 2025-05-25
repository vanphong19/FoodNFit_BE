package com.vanphong.foodnfitbe.presentation.viewmodel.request;


public record RegisterRequest (
     String email,
     String password,
     String confirmPassword
){}
