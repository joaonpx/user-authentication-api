package com.example.auth.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterDTO(
        Long id,

        @NotBlank(message = "Name is required!")
        String name,

        @NotBlank(message = "Email is required!")
        @Email
        String email,

        @NotBlank(message = "Password is required!")
        @Size(min = 6, max = 20, message = "The password must contain between 6 and 20 characters!")
        String password
) {
}
