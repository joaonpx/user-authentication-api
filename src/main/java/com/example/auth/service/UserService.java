package com.example.auth.service;

import com.example.auth.dto.UserDTO;
import com.example.auth.dto.UserRegisterDTO;
import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserDTO save(UserRegisterDTO userRegisterDTO) {
    User user = new User();
    BeanUtils.copyProperties(userRegisterDTO, user);
    User savedUser = userRepository.save(user);
    return new UserDTO(savedUser);
  }

  public void delete(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);

    if (optionalUser.isPresent()) {
      userRepository.delete(optionalUser.get());
    } else {
      throw new RuntimeException("User not found with ID: " + id);
    }
  }

  public UserDTO update(UserRegisterDTO userRegisterDTO) {
    User user = new User();
    BeanUtils.copyProperties(userRegisterDTO, user);

    Optional<User> optionalUser = userRepository.findById(user.getId());

    if (optionalUser.isPresent()) {
      return new UserDTO(userRepository.save(optionalUser.get()));
    } else {
      throw new RuntimeException("User not found");
    }
  }

  public UserDTO get(Long id) {
    Optional<User> optionalUser = userRepository.findById(id);

    if (optionalUser.isPresent()) {
      return new UserDTO(optionalUser.get());
    } else {
      throw new RuntimeException("User not found with ID: " + id);
    }
  }

  public List<UserDTO> getAll() {
    return userRepository
            .findAll()
            .stream()
            .map(UserDTO::new)
            .toList();
  }
}
