package com.example.auth.controller;

import com.example.auth.config.security.TokenService;
import com.example.auth.dto.LoginDTO;
import com.example.auth.dto.TokenDTO;
import com.example.auth.dto.UserDetailsDTO;
import com.example.auth.dto.UserRegisterDTO;
import com.example.auth.model.User;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@Slf4j
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
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid LoginDTO loginDTO) {
    try {
      UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
              new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password());

      Authentication auth = authenticationManager.authenticate(usernamePasswordAuthToken);

      log.info("c=AuthenticationManager m=authenticate s=Success");

      String token = tokenService.generateToken((User) auth.getPrincipal());

      UUID id = userService.findByEmail(loginDTO.email()).id();

      log.info("c=AuthController m=login s=Success");

      return ResponseEntity.ok(new TokenDTO(id, token));

    } catch (BadCredentialsException e) {
      log.info("c=AuthController m=login s=Failed msg=Bad Credentials");
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    } catch (Exception e) {
      log.error("c=AuthController m=login s=Failed msg=Unexpected Error: {}", e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDetailsDTO register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
    UserDetailsDTO savedUser = userService.save(userRegisterDTO);

    log.info("c=AuthController m=register msg=Success");

    return savedUser;
  }
}
