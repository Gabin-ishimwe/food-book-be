package com.backend.app.foodbook.auth.dto;

import com.backend.app.foodbook.validation.passwordValidation.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
