package com.example.auth.service;

import com.example.auth.dto.UserDetailsDTO;
import com.example.auth.dto.UserRegisterDTO;
import com.example.auth.model.User;
import com.example.auth.model.UserNotFoundException;
import com.example.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserDetailsDTO save(UserRegisterDTO userRegisterDTO) {
    String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterDTO.password());

    User userToSave = convertUserRegisterDtoToUser(userRegisterDTO);
    userToSave.setPassword(encryptedPassword);

    User savedUser = userRepository.save(userToSave);

    log.info("c=UserService m=save s=Success msg=UserId:{}", savedUser.getId());

    return new UserDetailsDTO(savedUser);
  }

  public void delete(UUID id) {
    User userToDelete = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

    userRepository.delete(userToDelete);

    log.info("c=UserService m=delete s=Success");
  }

  public UserDetailsDTO update(UserRegisterDTO userRegisterDTO) {
    User userToUpdate = convertUserRegisterDtoToUser(userRegisterDTO);

    userRepository.findById(userToUpdate.getId()).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userToUpdate.getId()));

    User updatedUser = userRepository.save(userToUpdate);

    log.info("c=UserService m=update s=Success msg=UserId:{}", updatedUser.getId());

    return new UserDetailsDTO(updatedUser);
  }

  public UserDetailsDTO findById(UUID id) {
    User foundUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

    log.info("c=UserService m=findById s=Success msg=UserId:{}", foundUser.getId());

    return new UserDetailsDTO(foundUser);
  }

  public UserDetailsDTO findByEmail(String email) {
    User foundUser = userRepository.findByEmail(email);

    log.info("c=UserService m=findByEmail s=Success msg=UserId:{}", foundUser.getId());

    return new UserDetailsDTO(foundUser);
  }

  public Page<User> getAll(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  private User convertUserRegisterDtoToUser(UserRegisterDTO userRegisterDTO) {
    User user = new User();
    BeanUtils.copyProperties(userRegisterDTO, user);

    return user;
  }
}
