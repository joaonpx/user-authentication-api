package com.example.auth.dto;

import com.example.auth.model.User;

public record UserDetailsDTO(
        Long id,
        String name,
        String email
) {
  public UserDetailsDTO(User user) {
    this(user.getId(), user.getName(), user.getEmail());
  }
}
