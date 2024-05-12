package com.example.auth.dto;

import com.example.auth.model.User;

public record UserDTO(
        Long id,
        String name,
        String email
) {
  public UserDTO(User user) {
    this(user.getId(), user.getName(), user.getEmail());
  }
}
