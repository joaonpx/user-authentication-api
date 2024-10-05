package com.example.auth.dto;

import com.example.auth.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserRegisterDTO(
        UUID id,

        @NotBlank(message = "Username is required!")
        @Pattern(regexp = "^[a-z0-9._]+$", message = "Username must be in lowercase, with no accents or special characters, and may only contain . and _")
        String username,

        @NotBlank(message = "Display name is required!")
        String displayName,

        @NotBlank(message = "Email is required!")
        @Email(message = "Invalid email!")
        String email,

        @NotBlank(message = "Password is required!")
        @Size(min = 6, max = 20, message = "The password must contain between 6 and 20 characters!")
        String password,

        UserRole role
) {
}
