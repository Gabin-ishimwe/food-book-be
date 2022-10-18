package com.backend.app.foodbook.auth.dto;

import com.backend.app.foodbook.validation.passwordValidation.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDto {

    @NotBlank(
            message = "Email is required"
    )
    @Email(
            message = "Email must be valid"
    )
    private String email;

    @NotBlank(
            message = "PasswordAnnotation is required"
    )
    @Password
    private String password;
}
