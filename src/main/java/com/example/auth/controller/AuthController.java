package com.example.auth.controller;

import com.example.auth.config.security.TokenService;
import com.example.auth.dto.LoginDTO;
import com.example.auth.dto.TokenDTO;
import com.example.auth.dto.UserDetailsDTO;
import com.example.auth.dto.UserRegisterDTO;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO) {
    UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
            new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());

    Authentication auth = authenticationManager.authenticate(usernamePasswordAuthToken);

    String token = tokenService.generateToken((User) auth.getPrincipal());

    return ResponseEntity.ok(new TokenDTO(token));
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDetailsDTO register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
    UserDetailsDTO savedUser = null;
    savedUser = userService.save(userRegisterDTO);
    return savedUser;
  }
}
