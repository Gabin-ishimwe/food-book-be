package com.backend.app.foodbook.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRegisterDto {
    private String message;
    private String token;
}
