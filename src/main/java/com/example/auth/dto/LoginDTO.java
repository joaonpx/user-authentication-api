package com.example.auth.dto;

import com.example.auth.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "Email is required!")
        @Email(message = "Invalid email!")
        String email,

        @NotBlank(message = "Password is required!")
        @Size(min = 6, max = 20, message = "The password must contain between 6 and 20 characters!")
        String password
) {
}
