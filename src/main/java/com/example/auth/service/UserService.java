package com.example.auth.service;

import com.example.auth.dto.UserDetailsDTO;
import com.example.auth.dto.UserRegisterDTO;
import com.example.auth.model.User;
import com.example.auth.model.UserNotFoundException;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserDetailsDTO save(UserRegisterDTO userRegisterDTO) {

    String encryptedPassword = new BCryptPasswordEncoder().encode(userRegisterDTO.password());

    User user = new User();
    BeanUtils.copyProperties(userRegisterDTO, user);
    user.setPassword(encryptedPassword);

    User savedUser = userRepository.save(user);
    return new UserDetailsDTO(savedUser);
  }

  public void delete(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);

    if (optionalUser.isPresent()) {
      userRepository.delete(optionalUser.get());
    } else {
      throw new UserNotFoundException("User not found with ID: " + id);
    }
  }

  public UserDetailsDTO update(UserRegisterDTO userRegisterDTO) {
    User user = new User();
    BeanUtils.copyProperties(userRegisterDTO, user);

    Optional<User> optionalUser = userRepository.findById(user.getId());

    User savedUser = userRepository.save(user);

    if (optionalUser.isPresent()) {
      return new UserDetailsDTO(savedUser);
    } else {
      throw new UserNotFoundException("User not found");
    }
  }

  public UserDetailsDTO get(Long id) {
    return new UserDetailsDTO(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id)));
  }

  public List<UserDetailsDTO> getAll() {
    return userRepository
            .findAll()
            .stream()
            .map(UserDetailsDTO::new)
            .toList();
  }
}
