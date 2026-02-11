package com.example.jwt.service;

import com.example.jwt.entity.User;
import com.example.jwt.exception.UserNotFoundException;
import com.example.jwt.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public void saveUser(User user) {
    userRepository.save(user);
  }

  public boolean existsByUsernameOrEmail(String username, String email) {
    return userRepository.existsByUsernameOrEmail(username, email);
  }

  public void deleteAllUsers() {
    userRepository.deleteAll();
  }

  public User findUser(String email) {
    Optional<User> optional = userRepository.findByEmail(email);
    if (optional.isEmpty()) {
      throw new UserNotFoundException("Couldnt find a user");
    }
    return optional.get();
  }
}
