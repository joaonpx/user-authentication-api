package com.example.auth.controller;

import com.example.auth.dto.UserDetailsDTO;
import com.example.auth.dto.UserRegisterDTO;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public void deleteUser(@PathVariable Long id) {
    userService.delete(id);
  }

  @PutMapping("/update")
  @ResponseStatus(HttpStatus.OK)
  public UserDetailsDTO updateUser(@RequestBody @Valid UserRegisterDTO user) {
    return userService.update(user);
  }

  @GetMapping("/{id}/info")
  @ResponseStatus(HttpStatus.OK)
  public UserDetailsDTO getUserInfo(@PathVariable Long id) {
    return userService.get(id);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserDetailsDTO> getAllUsers() {
    return userService.getAll();
  }
}
