package com.example.auth.dto;

import com.example.auth.model.User;

import java.util.UUID;

public record UserDetailsDTO(
        UUID id,
        String username,
        String displayName,
        String email
) {
  public UserDetailsDTO(User user) {
    this(user.getId(), user.getUsername(), user.getDisplayName(), user.getEmail());
  }
}
