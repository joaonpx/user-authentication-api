package com.example.auth.controller;

import com.example.auth.dto.UserDetailsDTO;
import com.example.auth.dto.UserRegisterDTO;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/add")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDetailsDTO addUser(@RequestBody @Valid UserRegisterDTO user) {
    return userService.save(user);
  }

  @DeleteMapping("/delete/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable UUID id) {
    userService.delete(id);
  }

  @PutMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  public UserDetailsDTO updateUser(@RequestBody @Valid UserRegisterDTO user) {
    return userService.update(user);
  }

  @GetMapping("/{id}/info")
  @ResponseStatus(HttpStatus.OK)
  public UserDetailsDTO getUserInfo(@PathVariable UUID id) {
    return userService.findById(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<User> getAllUsers(@PageableDefault(size = 5, page = 0) Pageable pageable) {
    return userService.getAll(pageable);
  }
}
