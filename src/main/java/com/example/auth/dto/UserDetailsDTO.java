package com.example.auth.dto;

import com.example.auth.model.User;
import com.example.auth.model.UserRole;

public record UserDetailsDTO(
        Long id,
        String name,
        String email,
        UserRole role
) {
  public UserDetailsDTO(User user) {
    this(user.getId(), user.getName(), user.getEmail(), user.getRole());
  }
}
