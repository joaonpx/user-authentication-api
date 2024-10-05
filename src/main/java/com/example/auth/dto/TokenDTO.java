package com.example.auth.dto;

import java.util.UUID;

public record TokenDTO(
        UUID id,
        String token
) {
}
